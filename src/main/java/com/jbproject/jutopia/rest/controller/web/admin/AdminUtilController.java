package com.jbproject.jutopia.rest.controller.web.admin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.constant.ServerUtilConstant;
import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.CorpModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.repository.CorpRepository;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin/util")
public class AdminUtilController {
    @Value("${opendart.secret}")
    private String dartSecret;

    private final AdminUtilService adminUtilService;

    @GetMapping("/main")
    public String goMain(
            HttpServletRequest request, HttpServletResponse response, Model model
            , MergeCorpDetailPayload mergeCorpDetailPayload
    ) {

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
            , @RequestParam("file") MultipartFile multipartFile
            ,RedirectAttributes redirectAttributes
    ){
        try {
            DataFormatter dataFormatter = new DataFormatter();
            commandMap = init(request, commandMap);
            InputStream inputStream = file.getInputStream();
            //Workbook workbook = WorkbookFactory.create(inputStream);
            // 엑셀 파일 읽기 로직을 구현합니다.
            int insertCnt = 0;

            //손익보고서 insert parameter
            Map<String, Object> paramMap = new HashMap<String, Object>();

            //필수값 5코드
            String BSNS_YEAR = commandMap.get("BSNS_YEAR").toString();
            String REPRT_CODE = commandMap.get("REPRT_CODE").toString();
            String REPRT_NM = commandMap.get("REPRT_NM").toString();
            String SJ_DIV = commandMap.get("SJ_DIV").toString();
            String SJ_NM = commandMap.get("SJ_NM").toString();
            paramMap.put("BSNS_YEAR", BSNS_YEAR);
            paramMap.put("REPRT_CODE", REPRT_CODE);
            paramMap.put("REPRT_NM", REPRT_NM);
            paramMap.put("SJ_DIV", SJ_DIV);
            paramMap.put("SJ_NM", SJ_NM);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
            int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
                if (row != null) {
                    String accountNm = row.getCell(10).getStringCellValue().replace("ifrs_", "ifrs-full_");
                    if(	//20230705 EPS 넣으려고 임시로 주석처리
                            accountNm.equals("ifrs-full_Revenue") || accountNm.equals("dart_OperatingIncomeLoss") || accountNm.equals("ifrs-full_ProfitLoss") ||
                                    accountNm.equals("ifrs-full_BasicEarningsLossPerShare")
                    ) {
                        int cells = row.getPhysicalNumberOfCells();
                        for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
                            XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
                            String value = "";
                            if(cell != null) {
                                switch (cell.getCellType()) {
                                    case NUMERIC:
                                        value = cell.getNumericCellValue() + "";
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
                                if(columnIndex == 1) {
                                    value = value.replace("[", "").replace("]", "");
                                }else if(columnIndex == 7) {
                                    String[] strList = cell.toString().split("-");
                                    String month = strList[1].replace("월", "");
                                    if( Integer.parseInt(strList[1].replace("월", "")) < 10) {
                                        month = "0"+month;
                                    }
                                    String clsDate = strList[2]+"-"+month+"-"+strList[0];
                                    System.out.println("test2 : "+ clsDate);
                                    value = clsDate + "";
                                }else if(columnIndex == 10) {
                                    value = value.replace("ifrs_", "ifrs-full_");
                                }
                                paramMap.put("COL_"+columnIndex, value);
                            }
                        }
                        System.out.println(paramMap.toString());
                        Integer resultInt = adminService.mergeReport(paramMap);
                        insertCnt++;
                    }
                }
            }
            System.out.println("총 "+insertCnt+" 건 입력");
            return getMessageModel("msgAndRedirect", "엑셀업로드가 완료되었습니다.", "/cms/admin/exUp");
        } catch (IOException e) {
            e.printStackTrace();
            return getMessageModel("msgAndRedirect", e.toString(), "/cms/admin/exUp");
        }
    }

}
