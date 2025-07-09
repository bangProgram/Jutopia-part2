package com.jbproject.jutopia.rest.controller.web.admin;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.dto.model.XmlCorpModel;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.dto.result.CommCodeResult;
import com.jbproject.jutopia.rest.dto.result.MergeResult;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import com.jbproject.jutopia.rest.service.CommCodeService;
import com.jbproject.jutopia.rest.service.NyCorpSyncService;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin/util")
public class AdminUtilController {
    @Value("${opendart.secret}")
    private String dartSecret;

    private final AdminUtilService adminUtilService;
    private final CommCodeService commCodeService;
    private final NyCorpSyncService nyCorpSyncService;

    @GetMapping("/main")
    public String goMain(
            HttpServletRequest request, HttpServletResponse response, Model model
            , MergeCorpDetailPayload mergeCorpDetailPayload
            , MergeCorpReportPayload mergeCorpReportPayload
            , MergeCorpCisStatPayload mergeCorpCisStatPayload
    ) {

        List<CommCodeResult> quarterlyReportTypeList = commCodeService.getCommCodeListByGroupCode(CommonConstatns.QUARTERLY_REPORT_TYPE);
        List<CommCodeResult> reportTypeList =  commCodeService.getCommCodeListByGroupCode(CommonConstatns.REPORT_TYPE);

        model.addAttribute("quarterlyReportTypeList",quarterlyReportTypeList);
        model.addAttribute("reportTypeList",reportTypeList);
        model.addAttribute("mergeCorpReportPayload",mergeCorpReportPayload);
        model.addAttribute("mergeCorpDetailPayload",mergeCorpDetailPayload);
        model.addAttribute("mergeCorpCisStatPayload",mergeCorpCisStatPayload);
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
            adminUtilService.mergeCorpDetail(payload);
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
            , MergeCorpReportPayload payload
            , RedirectAttributes redirectAttributes
    ){
        try {
            int createCnt = 0;
            int updateCnt = 0;

            if(payload.getReportType().equals("CIS")){
                MergeResult mergeResult = adminUtilService.mergeCorpCis(payload, file);
                createCnt = mergeResult.getCreateCnt();
                updateCnt = mergeResult.getUpdateCnt();
            } else if (payload.getReportType().equals("BS")){
                MergeResult mergeResult = adminUtilService.mergeCorpCis(payload, file);
                createCnt = mergeResult.getCreateCnt();
                updateCnt = mergeResult.getUpdateCnt();
            }

            String msg = createCnt+" 건 입력 / " +updateCnt+"건 수정 : 엑셀업로드가 완료되었습니다.";
            System.out.println("JB msg : "+msg);
            redirectAttributes.addFlashAttribute("serverMessage", msg);
            return new RedirectView("/admin/util/main") ;
        } catch (Exception e) {
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage", "엑셀업로드에 실패했습니다..");
            return new RedirectView("/admin/util/main") ;
        }
    }

    @PostMapping("/cis/stat")
    public RedirectView mergeCorpCisStat(
            HttpServletRequest request, HttpServletResponse response, Model model
            , MergeCorpCisStatPayload payload
            , RedirectAttributes redirectAttributes
    ){
        try {
            int createCnt = 0;
            int updateCnt = 0;

            Long start = System.currentTimeMillis();

            MergeResult mergeResult = adminUtilService.mergeCisStat(payload);
            Long end = System.currentTimeMillis();
            createCnt = mergeResult.getCreateCnt();
            updateCnt = mergeResult.getUpdateCnt();

            String msg = createCnt+" 건 입력 / " +updateCnt+"건 수정 : 엑셀업로드가 완료되었습니다. / 작업시간 : "+(end-start)+" m초 ";
            System.out.println("JB msg : "+msg);
            redirectAttributes.addFlashAttribute("serverMessage", msg);
            return new RedirectView("/admin/util/main") ;
        } catch (Exception e) {
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage", "재무 통계정보 수정에 실패했습니다..");
            return new RedirectView("/admin/util/main") ;
        }
    }


    @PostMapping("/ny-corp/merge")
    public RedirectView mergeNyCorpFromNaver(
            HttpServletRequest request, HttpServletResponse response, Model model
            , RedirectAttributes redirectAttributes
    ){
        try {
            System.out.println("ny-corp/merge start!");
            adminUtilService.mergeNyCorpDetail();
            redirectAttributes.addFlashAttribute("serverMessage","뉴욕 기업 데이터 수정을 완료했습니다.");
            return new RedirectView("/admin/util/main") ;

        }catch (Exception e){
            System.out.println("error : "+e);
            redirectAttributes.addFlashAttribute("serverMessage","뉴욕 기업 데이터 수정에 실패했습니다.");
            return new RedirectView("/admin/util/main") ;
        }

    }



    @PostMapping("/ny-corp/report")
    public RedirectView mergeNyCorpReportFromCompanyFacts(
            HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("file") MultipartFile zipFile
    ) {
        try {
            int totalCorp = nyCorpSyncService.uploadAndParseCompanyFacts(zipFile);

            redirectAttributes.addFlashAttribute("serverMessage","총 " + totalCorp + "건 기업의 재무 데이터를 적재했습니다.");
            return new RedirectView("/admin/util/main") ;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("serverMessage","처리 중 오류 발생: " + e.getMessage());
            return new RedirectView("/admin/util/main") ;
        }
    }


    @PostMapping("/ny-corp/report/test")
    public RedirectView mergeNyCorpReportFromCik(
            HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            int totalCorp = nyCorpSyncService.mergeNyCorpCis();

            redirectAttributes.addFlashAttribute("serverMessage","총 " + totalCorp + "건 기업의 재무 데이터를 적재했습니다.");
            return new RedirectView("/admin/util/main") ;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("serverMessage","처리 중 오류 발생: " + e.getMessage());
            return new RedirectView("/admin/util/main") ;
        }
    }



}
