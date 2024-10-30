package com.jbproject.jutopia.rest.controller.web.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/util")
public class AdminUtilController {
    @Value("${opendart.secret}")
    private String dartSecret;

    @GetMapping("/main")
    public String goMain(Model model) {

        return "/admin/util/mainPage";
    }


    @PostMapping("/dart/corp")
    public String mergeCorpFromDart(
            HttpServletRequest request, HttpServletResponse response, Model model
            , @RequestParam("file") MultipartFile file
    ){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("JB test 0");
            XmlMapper xmlMapper = new XmlMapper();
            System.out.println("JB test 00");
            XmlCorpModel xmlCorpModel = objectMapper.readValue(file.getInputStream(), XmlCorpModel.class);
            System.out.println("XmlCorpModel : "+xmlCorpModel);

            // XML 파일 로드
            System.out.println("JB test 1");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            System.out.println("JB test 2");
            DocumentBuilder builder = factory.newDocumentBuilder();
            System.out.println("JB test 3");
            Document document = builder.parse(file.getInputStream());
            System.out.println("JB test 4");

            System.out.println("JB test 5");
//            Map<String,Object> data = objectMapper.readValue(file.getInputStream(), Map.class);
//            System.out.println("data : "+data);

            Element root = document.getDocumentElement();
            System.out.println("JB test 6");

            // "list" 요소 가져오기
            NodeList list = root.getElementsByTagName("list");
            System.out.println("JB test 7");
            for (int i = 0; i < list.getLength(); i++) {
                System.out.println("JB test 8");
                Node node = list.item(i);
                System.out.println("JB test 9");
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Map<String,Object> param = new HashMap<>();
                    Element element = (Element) node;
                    String corpCode = element.getElementsByTagName("corp_code").item(0).getTextContent();
                    String corpName = element.getElementsByTagName("corp_name").item(0).getTextContent();
                    String stockCode = element.getElementsByTagName("stock_code").item(0).getTextContent();
                    String modifyDate = element.getElementsByTagName("modify_date").item(0).getTextContent();

                    // 파싱한 데이터 활용 또는 출력
                    System.out.print("Corp Code: " + corpCode);
                    System.out.print(" Corp Name: " + corpName);
                    System.out.print(" Stock Code: " + stockCode);
                    System.out.print(" Modify Date: " + modifyDate + "\n");

                    param.put("CORP_CODE", corpCode);
                    param.put("CORP_NAME", corpName);
                    param.put("STOCK_CODE", stockCode);
                    param.put("MODIFY_DATE", modifyDate);

                }
            }

            model.addAttribute("serverMessage","기업 데이터 수정을 완료했습니다.");
        }catch (Exception e){
            System.out.println("error : "+e);
            model.addAttribute("serverMessage","기업 데이터 수정에 실패했습니다.");
        }

        return "/admin/util/mainPage";
    }

    @GetMapping("/dart/corp/detail")
    public String mergeCorpDetailFromDart(
            HttpServletRequest request, HttpServletResponse response, Model model
            ,@RequestParam("corpCode") String corpCode
    ){
        String gubn = "" ;      //commandMap.get("GUBN").toString();
        String stLimit = "" ;   //commandMap.get("stLimit").toString();
        String edLimit = "" ;   //commandMap.get("edLimit").toString();

        List<Map<String,Object>> getCorpList = new ArrayList<>() ; // adminService.getCorpListForMerge(commandMap);

        String apiUrl = "https://opendart.fss.or.kr/api/company.json";
        // 파라미터 설정

        if(corpCode != null) {
            try {
                String parameters = "?crtfc_key="+dartSecret+"&corp_code="+corpCode;

                // URL과 파라미터 조합
                String uri = apiUrl + parameters;
                System.out.println("uri : "+uri);

                URL url = new URL(uri);
                InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
                ObjectMapper objectMapper = new ObjectMapper();

                CorpDetailModel corpDetail = objectMapper.convertValue(isr, CorpDetailModel.class);
                System.out.println("corpDetail : "+corpDetail);

                /*
                for(int i=0; i<getCorpList.size(); i++) {
                    //String corpCode = getCorpList.get(i).get("CORP_CODE").toString();
                    String parameters = "?crtfc_key="+"77ec5d06256850ef6a8b4f038905ca4344e6dc93"+"&corp_code="+corpCode;

                    // URL과 파라미터 조합
                    String uri = apiUrl + parameters;
                    System.out.println("uri : "+uri);

                    URL url = new URL(uri);
                    InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
                    ObjectMapper objectMapper = new ObjectMapper();

                    CorpDetailModel corpDetail = objectMapper.convertValue(isr, CorpDetailModel.class);
                    System.out.println("corpDetail : "+corpDetail);

                    *//*
                    String CORP_CODE = object.get("corp_code").toString();
                    String CORP_NAME = object.get("corp_name").toString();
                    String STOCK_NAME = object.get("stock_name").toString();
                    String STOCK_CODE = object.get("stock_code").toString();
                    String CEO_NM = object.get("ceo_nm").toString();
                    String CORP_CLS = object.get("corp_cls").toString();
                    String JURIR_NO = object.get("jurir_no").toString();
                    String BIZR_NO = object.get("bizr_no").toString();
                    String ADRES = object.get("adres").toString();
                    String HM_URL = object.get("hm_url").toString();
                    String IR_URL = object.get("ir_url").toString();
                    String PHN_NO = object.get("phn_no").toString();
                    String FAX_NO = object.get("fax_no").toString();
                    String INDUTY_CODE = object.get("induty_code").toString();
                    String EST_DT = object.get("est_dt").toString();
                    String ACC_MT = object.get("acc_mt").toString();

                    paramMap.put("CORP_CODE", CORP_CODE);
                    paramMap.put("CORP_NAME", CORP_NAME);
                    paramMap.put("STOCK_NAME", STOCK_NAME);
                    paramMap.put("STOCK_CODE", STOCK_CODE);
                    paramMap.put("CEO_NM", CEO_NM);
                    paramMap.put("CORP_CLS", CORP_CLS);
                    paramMap.put("JURIR_NO", JURIR_NO);
                    paramMap.put("BIZR_NO", BIZR_NO);
                    paramMap.put("ADRES", ADRES);
                    paramMap.put("HM_URL", HM_URL);
                    paramMap.put("IR_URL", IR_URL);
                    paramMap.put("PHN_NO", PHN_NO);
                    paramMap.put("FAX_NO", FAX_NO);
                    paramMap.put("INDUTY_CODE", INDUTY_CODE);
                    paramMap.put("EST_DT", EST_DT);
                    paramMap.put("ACC_MT", ACC_MT);
                    *//*

                }
                */
                model.addAttribute("serverMessage","기업개황을 성공적으로 Merge 했습니다.");
            }catch (Exception e){

                model.addAttribute("serverMessage","기업개황을 Merge 를 실패했습니다.");
            }

        }

        return "/admin/util/mainPage";
    }

}
