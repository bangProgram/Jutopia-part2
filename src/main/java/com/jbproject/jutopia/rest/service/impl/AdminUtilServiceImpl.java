package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.CorpModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.model.result.MergeResult;
import com.jbproject.jutopia.rest.repository.CommCodeRepository;
import com.jbproject.jutopia.rest.repository.CorpCisRepository;
import com.jbproject.jutopia.rest.repository.CorpDetailRepository;
import com.jbproject.jutopia.rest.repository.CorpRepository;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminUtilServiceImpl implements AdminUtilService {

    private final CorpRepository corpRepository;

    private final CorpDetailRepository corpDetailRepository;
    private final CorpCisRepository corpCisRepository;
    private final CommCodeRepository commCodeRepository;

    public List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload){
        System.out.println("test3");
        return corpRepository.getCorpListByMergeCorpDetailPayload(payload);
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

        System.out.println(" JB quarterlyReportCode : "+quarterlyReportCode);
        XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
        int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
        for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

            XSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
            if (row != null) {
                String corpCode = row.getCell(1).getStringCellValue().replace("[", "").replace("]", "");
                String accountId = row.getCell(10).getStringCellValue().replace("ifrs_", "ifrs-full_");

                if(	accounIdList.contains(accountId) ) {
                    int cells = row.getPhysicalNumberOfCells();

                    //손익보고서 Insert Model 구성
                    CorpCisModel corpCisModel = new CorpCisModel();


                    corpCisModel.setCorpCode(corpCode);
                    corpCisModel.setBsnsYear(bsnsYear);
                    corpCisModel.setQuarterlyReportCode(quarterlyReportCode);
                    corpCisModel.setQuarterlyReportName(quarterlyReportName);
                    corpCisModel.setAccountId(accountId);

                    Optional<CorpCisEntity> corpCisEntity = corpCisRepository.findById(new CorpCisKey(corpCode,bsnsYear,quarterlyReportCode,accountId));

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
            System.out.println("test 3 : "+ corpModel);
            CorpEntity newCorp = CorpEntity.builder()
                    .corpCode(corpModel.getCorpCode())
                    .corpName(corpModel.getCorpName())
                    .stockCode(corpModel.getStockCode())
                    .modifyDate(LocalDate.parse(corpModel.getModifyDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .build();

            System.out.println("test 3 : "+ corpModel);
            corpRepository.save(newCorp);
        }
    }

    public void saveCorpDetail(CorpDetailModel corpDetailModel){

        CorpDetailEntity newCorpDetail = CorpDetailEntity.builder()
                .corpCode(corpDetailModel.getCorpCode())
                .corpName(corpDetailModel.getCorpName())
                .stockName(corpDetailModel.getStockName())
                .stockCode(corpDetailModel.getStockCode())
                .ceoName(corpDetailModel.getCeoNm())
                .corpCls(corpDetailModel.getCorpCls())
                .jurirNo(corpDetailModel.getJurirNo())
                .bizrNo(corpDetailModel.getBizrNo())
                .address(corpDetailModel.getAdres())
                .hmUrl(corpDetailModel.getHmUrl())
                .irUrl(corpDetailModel.getIrUrl())
                .phnNo(corpDetailModel.getPhnNo())
                .faxNo(corpDetailModel.getFaxNo())
                .indutyCode(corpDetailModel.getIndutyCode())
                .estDate(corpDetailModel.getEstDt())
                .accMt(corpDetailModel.getAccMt())
                .build();

        corpDetailRepository.save(newCorpDetail);
    }

    public void updateCorpCis(CorpCisEntity entity){
        corpCisRepository.save(entity);
    }

}
