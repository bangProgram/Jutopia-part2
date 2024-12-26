package com.jbproject.jutopia.rest.controller.web.admin;

import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.constant.CommonErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.dto.result.AuthResult;
import com.jbproject.jutopia.rest.dto.result.CommCodeResult;
import com.jbproject.jutopia.rest.service.AdminAuthService;
import com.jbproject.jutopia.rest.service.CommCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/auth")
public class AdminAuthController {

    private final CommCodeService commCodeService;
    private final AdminAuthService adminAuthService;

    @GetMapping({
            "/main",
            "/main/{roleType}"
    })
    public String goMain(
            HttpServletRequest request, HttpServletResponse response, Model model
            , @PathVariable(value = "roleType", required = false) String roleType
    ) throws Exception{
        // 현재 접속된 그룹 전송
        if(roleType == null){
            roleType = Role.VISITOR.name();
        }else{
            List<String> roleTypeList = commCodeService.getCommCodeListByGroupCode(CommonConstatns.ROLE_TYPE).stream().map(
                    CommCodeResult::getCode
            ).toList();
            if(!roleTypeList.contains(roleType)){
                ExceptionProvider.sendErrorResponse(request, response, CommonErrorCode.COMMON_404_02);
            }
        }
        model.addAttribute("roleType",roleType);

        // 그룹별 메뉴 리스트 제공
        AuthResult authResult = adminAuthService.getMenuRoleList(roleType);

        System.out.println("authResult : "+authResult.getUserMenuRoleList());
        model.addAttribute("authResult", authResult);

        // 그룹별 메뉴 리스트 제공 및 추가,수정을 위한 그룹 플래그 전송
        List<CommCodeResult> roleTypes = commCodeService.getCommCodeListByGroupCode(CommonConstatns.ROLE_TYPE);
        model.addAttribute("roleTypes",roleTypes);
        System.out.println("request.getServletPath() : "+request.getServletPath());
        return "/admin/auth/mainPage";
    }

    @PostMapping("/main/cud")
    public RedirectView cudProc(
            @RequestParam(value = "roleType", required = false) String roleType,
            @RequestParam(name = "menuId", required = false) List<Long> menuIds,
            @RequestParam(name = "cudMenuId", required = false) List<Long> cudMenuIds,
            @RequestParam(name = "chkMenuId", required = false) List<Long> chkMenuIds,
            RedirectAttributes redirectAttributes
    ){

        adminAuthService.cudRoleMenu(roleType, menuIds, cudMenuIds, chkMenuIds);
        redirectAttributes.addFlashAttribute("serverMessage", roleType+"의 접근 권한이 정상적으로 부여되었습니다.");
        return new RedirectView("/admin/auth/main/"+roleType);
    }


}
