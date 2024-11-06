package com.jbproject.jutopia.rest.controller.web.admin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.constant.ServerUtilConstant;
import com.jbproject.jutopia.rest.entity.CommCodeEntity;
import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.CorpModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.repository.CorpRepository;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import com.jbproject.jutopia.rest.service.CommCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin/util")
public class AdminUtilController {
    @Value("${opendart.secret}")
    private String dartSecret;

    private final AdminUtilService adminUtilService;
    private final CommCodeService commCodeService;

    @GetMapping("/main")
    public String goMain(
            HttpServletRequest request, HttpServletResponse response, Model model
            , MergeCorpDetailPayload mergeCorpDetailPayload
            , MergeCorpReportPayload mergeCorpReportPayload
    ) {

        List<CommCodeResult> quarterlyReportTypeList = commCodeService.getCommCodeListByGroupCode(CommonConstatns.QUARTERLY_REPORT_TYPE);
        List<CommCodeResult> reportTypeList =  commCodeService.getCommCodeListByGroupCode(CommonConstatns.REPORT_TYPE);

        model.addAttribute("quarterlyReportTypeList",quarterlyReportTypeList);
        model.addAttribute("reportTypeList",reportTypeList);
        model.addAttribute("mergeCorpReportPayload",mergeCorpReportPayload);
        model.addAttribute("mergeCorpDetailPayload",mergeCorpDetailPayload);
        return "/admin/util/mainPage";
    }


    @PostMapping("/dart/corp")
    public RedirectView mergeCorpFromDart(
            HttpServletRequest request, HttpServletResponse response, Model model
            , @RequestParam("file") MultipartFile multipartFile
            , RedirectAttributes redirectAttributes
    ){
        try {

            XmlMapper objectMapper = new XmlMapper();

            XmlCorpModel xmlCorpModel = objectMapper.readValue(multipartFile.getInputStream(), XmlCorpModel.class);

            System.out.println("test 1");
            adminUtilService.saveCorp(xmlCorpModel);

            redirectAttributes.addFlashAttribute("serverMessage","기업 데이터 수정을 완료했습니다.");
            return new RedirectView("/admin/util/main") ;

        }catch (Exception e){
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage","기업 데이터 수정에 실패했습니다.");
            return new RedirectView("/admin/util/main") ;
        }

    }

    @PostMapping("/dart/corp/detail")
    public RedirectView mergeCorpDetailFromDart(
            HttpServletRequest request, HttpServletResponse response, Model model
            ,MergeCorpDetailPayload payload
            ,RedirectAttributes redirectAttributes
    ){
        // 파라미터 설정
        try {
            List<CorpResult> getCorpList = adminUtilService.getCorpListByMergeCorpDetailPayload(payload); // adminService.getCorpListForMerge(commandMap);
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

                adminUtilService.saveCorpDetail(corpDetail);
                cnt++;

                if(cnt == Integer.parseInt(ServerUtilConstant.CORP_MERGE_LIMIT.getValue())){
                    cnt = 0;
                    Thread.sleep(Integer.parseInt(ServerUtilConstant.CORP_MERGE_DELAY.getValue()));
                }
            }

            redirectAttributes.addFlashAttribute("serverMessage","기업개황을 성공적으로 Merge 했습니다.");
            return new RedirectView("/admin/util/main") ;
        }catch (Exception e){
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage","기업개황 Merge 에 실패했습니다.");
            return new RedirectView("/admin/util/main") ;
        }

    }


    @PostMapping("/dart/corp/report")
    public RedirectView mergeCorpReportFromDart(
            HttpServletRequest request, HttpServletResponse response, Model model
            , @RequestParam("file") MultipartFile file
            , MergeCorpReportPayload mergeCorpReportPayload
            , RedirectAttributes redirectAttributes
    ){
        try {
            List<CommCodeResult> accountType;
            List<String> accounIdList;

            if(mergeCorpReportPayload.getReportType().equals("CIS")){
                accountType = commCodeService.getCommCodeListByGroupCode(CommonConstatns.INCOME_STATEMENT);
                accounIdList = accountType.stream().map(CommCodeResult::getCode).toList();
            } else if (mergeCorpReportPayload.getReportType().equals("BS")){
                accountType = commCodeService.getCommCodeListByGroupCode(CommonConstatns.BALANCE_SHEET);
                accounIdList = accountType.stream().map(CommCodeResult::getCode).toList();
            } else {
                accountType = new ArrayList<>();
                accounIdList = new ArrayList<>();
            }

            InputStream inputStream = file.getInputStream();

            // 엑셀 파일 읽기 로직을 구현합니다.
            int insertCnt = 0;

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
            int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

                XSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
                if (row != null) {
                    String accountId = row.getCell(10).getStringCellValue().replace("ifrs_", "ifrs-full_");

                    if(	accounIdList.contains(accountId) ) {
                        int cells = row.getPhysicalNumberOfCells();
                        
                        //손익보고서 Insert Model 구성
                        CorpCisModel corpCisModel = new CorpCisModel();

                        // 회계년도, 분기정보 입력
                        corpCisModel.setBsnsYear(mergeCorpReportPayload.getBsnsYear());
                        corpCisModel.setQuarterlyReportCode(mergeCorpReportPayload.getQuarterlyReportCode());
                        corpCisModel.setQuarterlyReportName(mergeCorpReportPayload.getQuarterlyReportName());
                        corpCisModel.setAccountId(accountId);
                        
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
                                    case 1:
                                        corpCisModel.setCorpCode(value.replace("[", "").replace("]", ""));
                                        break;
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
                        adminUtilService.saveCorpCis(corpCisModel);
                        insertCnt++;
                    }
                }
            }
            System.out.println("총 "+insertCnt+" 건 입력");
            redirectAttributes.addFlashAttribute("serverMessage", "엑셀업로드가 완료되었습니다.");
            return new RedirectView("/admin/util/main") ;
        } catch (Exception e) {
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage", "엑셀업로드에 실패했습니다..");
            return new RedirectView("/admin/util/main") ;
        }
    }

}
