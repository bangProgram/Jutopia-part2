package com.jbproject.jutopia.rest.controller.web.admin;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.constant.CommonErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.service.CommCodeService;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/menu")
public class AdminMenuController {

    private final MenuService menuService;
    private final CommCodeService commCodeService;

    @GetMapping({
            "/main",
            "/main/{menuType}"
    })
    public String goMain(
            HttpServletRequest request, HttpServletResponse response, Model model
            , @PathVariable(value = "menuType", required = false) String menuType
            , MenuCudPayload payload
        ) throws Exception {
        // 현재 접속된 그룹 전송
        if(menuType == null){
            menuType = CommonConstatns.MENU_ROLE_USER;
        }else{
            List<String> menuTypeList = commCodeService.getCommCodeListByGroupCode(CommonConstatns.MENU_TYPE).stream().map(
                    CommCodeResult::getCode
            ).toList();
            if(!menuTypeList.contains(menuType)){
                ExceptionProvider.sendErrorResponse(request, response, CommonErrorCode.COMMON_404_01);
            }
        }
        payload.setMenuType(menuType);
        model.addAttribute("menuCudPayload", payload);

        // 그룹별 메뉴 리스트 제공
        List<MenuResult> result = menuService.getMenuList(menuType);
        for(MenuResult menu : result) {
            System.out.println("menu : "+menu);
        }
        model.addAttribute("menuResults", result);

        // 그룹별 메뉴 리스트 제공 및 추가,수정을 위한 그룹 플래그 전송
        List<CommCodeResult> menuTypes = commCodeService.getCommCodeListByGroupCode(CommonConstatns.MENU_TYPE);
        model.addAttribute("menuTypes",menuTypes);
        return "/admin/menu/mainPage";
    }

    @PostMapping("/main/cud")
    public RedirectView cudProc(
            HttpServletRequest request, Model model, MenuCudPayload payload
            , RedirectAttributes redirectAttributes
            ){
        Long menuId = payload.getMenuId();
        try {
            if(menuId == 0L){
                menuService.addMenu(payload);
                redirectAttributes.addFlashAttribute("serverMessage",payload.getMenuName() + " 메뉴 등록 완료.");
            }else{
                menuService.modMenu(payload);
                redirectAttributes.addFlashAttribute("serverMessage",payload.getMenuName() + " 메뉴 수정 완료.");
            }
            return new RedirectView("/admin/menu/main/"+payload.getMenuType());
        }catch (Exception e){
            log.error("error : {}",e.getLocalizedMessage());
            redirectAttributes.addFlashAttribute("serverMessage",e.getLocalizedMessage());
            return new RedirectView("/admin/menu/main/"+payload.getMenuType());
        }
    }


}
