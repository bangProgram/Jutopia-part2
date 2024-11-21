package com.jbproject.jutopia.rest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.constant.ServerUtilConstant;
import com.jbproject.jutopia.rest.entity.*;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.model.*;
import com.jbproject.jutopia.rest.model.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.model.result.*;
import com.jbproject.jutopia.rest.repository.*;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminUtilServiceImpl implements AdminUtilService {

    @Value("${opendart.secret}")
    private String dartSecret;

    private final CorpRepository corpRepository;

    private final CorpDetailRepository corpDetailRepository;
    private final CorpCisRepository corpCisRepository;
    private final CorpCisStatRepository corpCisStatRepository;
    private final CommCodeRepository commCodeRepository;

    public void mergeCorpDetail(MergeCorpDetailPayload payload) throws Exception{
        List<CorpResult> getCorpList = corpRepository.getCorpListByMergeCorpDetailPayload(payload); // adminService.getCorpListForMerge(commandMap);
        String apiUrl = "https://opendart.fss.or.kr/api/company.json";
        System.out.println("getCorpList : "+getCorpList);

        int cnt = 0;
        for(CorpResult corpResult : getCorpList){
            String corpCode = corpResult.getCorpCode();
            String parameters = "?crtfc_key="+dartSecret+"&corp_code="+corpCode;

            // URL과 파라미터 조합
            String uri = apiUrl + parameters;

            URL url = new URL(uri);
            InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            CorpDetailModel corpDetail = objectMapper.readValue(isr, CorpDetailModel.class);
            corpDetail.setCorpCode(corpCode);

            CorpDetailEntity newCorpDetail = new CorpDetailEntity(corpDetail);

            corpDetailRepository.save(newCorpDetail);
            cnt++;

            if(cnt == Integer.parseInt(ServerUtilConstant.CORP_MERGE_LIMIT.getValue())){
                cnt = 0;
                Thread.sleep(Integer.parseInt(ServerUtilConstant.CORP_MERGE_DELAY.getValue()));
            }
        }
    }

    public MergeResult mergeCorpCis(MergeCorpReportPayload payload, MultipartFile file) throws Exception{
        MergeResult mergeResult = new MergeResult();

        // 엑셀 타이틀 받아서 년도, 보고서타입 추출하는 로직 추가하기

        List<CommCodeResult> accountType = commCodeRepository.getCommCodeListByGroupCode(CommonConstatns.INCOME_STATEMENT);
        List<CommCodeResult> quarterlyReportType = commCodeRepository.getCommCodeListByGroupCode(CommonConstatns.QUARTERLY_REPORT_TYPE);
        List<String> accounIdList = accountType.stream().map(CommCodeResult::getCode).toList();

        InputStream inputStream = file.getInputStream();
        String[] fileName = Objects.requireNonNull(file.getOriginalFilename()).split("_");


        // 엑셀 파일 읽기 로직을 구현합니다.
        int updateCnt = 0;
        int createCnt = 0;

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);


        // 회계년도, 분기정보 입력
        String bsnsYear = fileName[0];
        String quarterlyReportName = "";
        String quarterlyReportCode = "";

        for(CommCodeResult quarterly : quarterlyReportType){
            if(quarterly.getCodeName().equals(fileName[1])){
                quarterlyReportName = quarterly.getCodeName();
                quarterlyReportCode = quarterly.getCode();
            }
        }

        XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
        int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
        for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

            XSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
            if (row != null) {
                String stockCode = row.getCell(1).getStringCellValue().replace("[", "").replace("]", "");
                String accountId = row.getCell(10).getStringCellValue().replace("ifrs_", "ifrs-full_");

                if(	accounIdList.contains(accountId) ) {
                    int cells = row.getPhysicalNumberOfCells();

                    //손익보고서 Insert Model 구성
                    CorpCisModel corpCisModel = new CorpCisModel();


                    corpCisModel.setStockCode(stockCode);
                    corpCisModel.setBsnsYear(bsnsYear);
                    corpCisModel.setQuarterlyReportCode(quarterlyReportCode);
                    corpCisModel.setQuarterlyReportName(quarterlyReportName);
                    corpCisModel.setAccountId(accountId);

                    Optional<CorpCisEntity> corpCisEntity = corpCisRepository.findById(new CorpCisKey(stockCode,bsnsYear,quarterlyReportCode,accountId));

                    for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
                        XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
                        String value = "";
                        if(cell != null) {
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    if( DateUtil.isCellDateFormatted(cell)) {
                                        Date date = cell.getDateCellValue();
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    }else{
                                        BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
                                        value = bigDecimal + "";
                                    }
                                    break;
                                case STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case BLANK:
                                    value = ""; // 빈 값을 빈 문자열로 처리하거나 필요에 따라 다른 처리를 수행하세요.
                                    break;
                                case ERROR:
                                    value = cell.getErrorCellValue() + "";
                                    break;
                            }
                           /*
                             * COL_0 재무제표종류
                             * COL_1 종목코드 = [회사코드]
                             * COL_2 회사명
                             * COL_3 시장구분
                             * COL_4 업종
                             * COL_5 업종명
                             * COL_6 결산월
                             * COL_7 결산기준일
                             * COL_8 보고서종류
                             * COL_9 통화
                             * COL_10 항목코드
                             * COL_11 항목명
                             * COL_12 당기 1분기 3개월
                             * COL_13 당기 1분기 누적
                             * COL_14 전기 1분기 3개월
                             * COL_15 전기 1분기 누적
                            */
                            switch (columnIndex){
                                case 7:
                                    corpCisModel.setClosingDate(LocalDate.parse(value));
                                    break;
                                case 8:
                                    corpCisModel.setQuarterlyReportName(value);
                                    break;
                                case 9:
                                    corpCisModel.setCurrency(value);
                                    break;
                                case 11:
                                    corpCisModel.setAccountName(value);
                                    break;
                                case 12:
                                    corpCisModel.setNetAmount(Long.valueOf(value));
                                    break;
                                case 13:
                                    corpCisModel.setAccumulatedNetAmount(Long.valueOf(value));
                                    break;
                                case 14:
                                    corpCisModel.setBefNetAmount(Long.valueOf(value));
                                    break;
                                case 15:
                                    corpCisModel.setBefAccumulatedNetAmount(Long.valueOf(value));
                                    break;

                            }
                        }
                    }
                    if(corpCisEntity.isPresent()){
                        System.out.println("CorpCis 존재 : 수정");
                        CorpCisEntity modCorpCis = corpCisEntity.get();
                        modCorpCis.updateCorpCis(corpCisModel);
                        updateCnt++;
                        corpCisRepository.save(modCorpCis);
                    }else{
                        System.out.println("CorpCis 존재 : 적재");
                        CorpCisEntity newCorpCis = new CorpCisEntity(corpCisModel);
                        createCnt++;
                        corpCisRepository.save(newCorpCis);
                    }
                }
            }
        }
        mergeResult.setCreateCnt(createCnt);
        mergeResult.setUpdateCnt(updateCnt);
        return mergeResult;
    }

    public Optional<CorpCisEntity> findById(CorpCisKey key){
        return corpCisRepository.findById(key);
    }

    public void saveCorp(XmlCorpModel xmlCorpModel){

        System.out.println("test 2 : "+xmlCorpModel.getCorpModels().getFirst());

        for(CorpModel corpModel : xmlCorpModel.getCorpModels()){
            CorpEntity newCorp = CorpEntity.builder()
                    .corpCode(corpModel.getCorpCode())
                    .corpName(corpModel.getCorpName())
                    .stockCode(corpModel.getStockCode())
                    .modifyDate(LocalDate.parse(corpModel.getModifyDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .build();

            corpRepository.save(newCorp);
        }
    }

    public void updateCorpCis(CorpCisEntity entity){
        corpCisRepository.save(entity);
    }

    public MergeResult mergeCisStat(MergeCorpCisStatPayload payload){
        List<CorpCisStatModel> corpCisResults = corpCisRepository.getCorpCisList(payload);

        MergeResult result = new MergeResult();
        int createCnt = 0;
        int updateCnt = 0;

        List<String> stockCodes = corpCisResults.stream().map(CorpCisStatModel::getStockCode).distinct().toList();
        List<String> accountIds = corpCisResults.stream().map(CorpCisStatModel::getAccountId).distinct().toList();

        Map<String, Map<String, List<CorpCisStatModel>>> corpCisGroup =
                corpCisResults.stream().collect(
                        Collectors.groupingBy(CorpCisStatModel::getStockCode,
                                Collectors.groupingBy(CorpCisStatModel::getAccountId)
                        )
                );

        for(String stockCode : stockCodes) {
            for(String accountId : accountIds){
                List<CorpCisStatModel> corpCisStatModelList =corpCisGroup.get(stockCode).get(accountId);
                if(corpCisStatModelList != null){
                    
                    corpCisStatModelList.sort(Comparator.comparing(CorpCisStatModel::getBsnsYear)
                            .thenComparing(CorpCisStatModel::getQuarterlyReportCode));

                    Map<String, NetStatModel> netStatModelMap = new HashMap<>();

                    for(int i=1; i<corpCisStatModelList.size(); i++){
                        CorpCisStatKey key = new CorpCisStatKey();
                        key.setStockCode(stockCode);
                        key.setAccountId(accountId);
                        key.setBsnsYear(corpCisStatModelList.get(i).getBsnsYear());
                        key.setQuarterlyReportCode(corpCisStatModelList.get(i).getQuarterlyReportCode());


                        Optional<CorpCisStatEntity> entity = corpCisStatRepository.findById(key);

                        //사업보고서일 경우 netAmount = AccNetAmount(i) - AccNetAmount(i-1)
                        String curQuarterlyCode = corpCisStatModelList.get(i).getQuarterlyReportCode();
                        
                        Long accumulatedNetAmount = corpCisStatModelList.get(i).getAccumulatedNetAmount() != null ? (Long) corpCisStatModelList.get(i).getAccumulatedNetAmount() : 0L;
                        Long befAccumulatedNetAmount = corpCisStatModelList.get(i-1).getAccumulatedNetAmount() != null ? (Long) corpCisStatModelList.get(i-1).getAccumulatedNetAmount() : 0L;
                        Long befYearAccumulatedNetAmount = corpCisStatModelList.get(i).getBefYearAccumulatedNetAmount() != null ? (Long) corpCisStatModelList.get(i).getBefYearAccumulatedNetAmount() : 0L;

                        Long netAmount = corpCisStatModelList.get(i).getNetAmount() != null ? (Long) corpCisStatModelList.get(i).getNetAmount() : 0L ;
                        Long befNetAmount = corpCisStatModelList.get(i-1).getNetAmount() != null ? (Long) corpCisStatModelList.get(i-1).getNetAmount() : 0L ;


                        if(curQuarterlyCode.equals("Q1")){
                            if(netStatModelMap.get("Q4") != null)
                                befNetAmount = netStatModelMap.get("Q4").getNetAmount();
                        }else if(curQuarterlyCode.equals("Q4")){
                            netAmount = accumulatedNetAmount-befAccumulatedNetAmount;
                        }

                        netStatModelMap.put(
                                curQuarterlyCode, NetStatModel.builder().netAmount(netAmount).befNetAmount(befNetAmount).build()
                        );

                        if(entity.isPresent()){
                            CorpCisStatEntity curEntity = entity.get();
                            curEntity.update(
                                    CorpCisStatModel.builder()
                                            .stockName(corpCisStatModelList.get(i).getStockName())
                                            .accountName(corpCisStatModelList.get(i).getAccountName())
                                            .befBsnsYear(corpCisStatModelList.get(i-1).getBsnsYear())
                                            .befQuarterlyReportCode(corpCisStatModelList.get(i-1).getQuarterlyReportCode())
                                            .befNetAmount(befNetAmount)
                                            .netAmount(netAmount)
                                            .befYearAccumulatedNetAmount(befYearAccumulatedNetAmount)
                                            .accumulatedNetAmount(accumulatedNetAmount)
                                            .build()
                            );
                            updateCnt++;
                            corpCisStatRepository.save(curEntity);
                        }else{
                            CorpCisStatEntity newEntity = CorpCisStatEntity.builder()
                                    .key(key)
                                    .stockName(corpCisStatModelList.get(i).getStockName())
                                    .accountName(corpCisStatModelList.get(i).getAccountName())
                                    .befBsnsYear(corpCisStatModelList.get(i-1).getBsnsYear())
                                    .befQuarterlyReportCode(corpCisStatModelList.get(i-1).getQuarterlyReportCode())
                                    .befNetAmount(befNetAmount)
                                    .netAmount(netAmount)
                                    .befYearAccumulatedNetAmount(befYearAccumulatedNetAmount)
                                    .accumulatedNetAmount(accumulatedNetAmount)
                                    .build();

                            createCnt++;
                            corpCisStatRepository.save(newEntity);
                        }
                    }
                }
            }
        }
        result.setCreateCnt(createCnt);
        result.setUpdateCnt(updateCnt);

        return result;

    }
}
