package com.jbproject.jutopia.rest.controller.web.admin;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.service.AdminAuthService;
import com.jbproject.jutopia.rest.service.CommCodeService;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/main/{roleType}")
    public String goMain(
            HttpServletRequest request, Model model
            , @PathVariable(value = "roleType", required = false) String roleType
    ){
        // 현재 접속된 그룹 전송
        model.addAttribute("roleType",roleType);

        // 그룹별 메뉴 리스트 제공
        AuthResult authResult = adminAuthService.getMenuRoleList(roleType);
        for(MenuResult menu : authResult.getUserMenuRoleList()){
            System.out.println("user menu : "+menu);
        }
        for(MenuResult menu : authResult.getAdminMenuRoleList()){
            System.out.println("Admin menu : "+menu);
        }
        model.addAttribute("authResult", authResult);

        // 그룹별 메뉴 리스트 제공 및 추가,수정을 위한 그룹 플래그 전송
        List<CommCodeResult> roleTypes = commCodeService.getCommCodeListByGroupCode(CommonConstatns.ROLE_TYPE);
        model.addAttribute("roleTypes",roleTypes);
        return "/admin/auth/mainPage";
    }

    @PostMapping("/cud")
    public RedirectView cudProc(
            @RequestParam(name = "roleType") String roleType,
            @RequestParam(name = "menuId", required = false) List<Long> menuIds,
            @RequestParam(name = "cudMenuId", required = false) List<Long> cudMenuIds,
            @RequestParam(name = "chkMenuId", required = false) List<Long> chkMenuIds,
            RedirectAttributes redirectAttributes
    ){
        // List<String>을 List<Long>으로 변환
//        List<Long> menuIds = menuIdStr.stream()
//                .filter(id -> id != null && !id.isEmpty()) // null 또는 빈 문자열 필터링
//                .map(Long::valueOf) // String을 Long으로 변환
//                .toList();
//
//        List<Long> cudMenuIds = cudMenuIdStr.stream()
//                .filter(id -> id != null && !id.isEmpty()) // null 또는 빈 문자열 필터링
//                .map(Long::valueOf) // String을 Long으로 변환
//                .toList();
//
//        List<Long> chkMenuIds = chkMenuIdStr.stream()
//                .filter(id -> id != null && !id.isEmpty()) // null 또는 빈 문자열 필터링
//                .map(Long::valueOf) // String을 Long으로 변환
//                .toList();

        adminAuthService.cudRoleMenu(roleType, menuIds, cudMenuIds, chkMenuIds);
        redirectAttributes.addFlashAttribute("successMessage", roleType+"의 접근 권한이 정상적으로 부여되었습니다.");
        return new RedirectView("/admin/auth/main/"+roleType);
    }


}
