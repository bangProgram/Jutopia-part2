package com.jbproject.jutopia.rest.controller.web.admin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

//        if(true) {
            try {
                System.out.println("test1");
                List<CorpResult> getCorpList = adminUtilService.getCorpListByMergeCorpDetailPayload(payload); // adminService.getCorpListForMerge(commandMap);
                System.out.println("test2");
                String apiUrl = "https://opendart.fss.or.kr/api/company.json";

                String parameters = "?crtfc_key="+dartSecret+"&corp_code="+"00126380";

                // URL과 파라미터 조합
                String uri = apiUrl + parameters;

                URL url = new URL(uri);
                InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
                ObjectMapper objectMapper = new ObjectMapper();
                CorpDetailModel corpDetail = objectMapper.readValue(isr, CorpDetailModel.class);
                corpDetail.setCorpCode("00126380");

                System.out.println("getCorpList : "+getCorpList);
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
                    corp_code
                    corp_name
                    stock_name
                    stock_code
                    ceo_name
                    corp_cls
                    jurir_no
                    bizr_no
                    address
                    hm_url
                    ir_url
                    phn_no
                    fax_no
                    induty_code
                    est_date
                    acc_mt


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
                redirectAttributes.addFlashAttribute("serverMessage","기업개황을 성공적으로 Merge 했습니다.");
                return new RedirectView("/admin/util/main") ;
            }catch (Exception e){
                System.out.println("error : "+e);
                redirectAttributes.addFlashAttribute("serverMessage","기업개황 Merge 에 실패했습니다.");
                return new RedirectView("/admin/util/main") ;
            }

//        }

    }

}
