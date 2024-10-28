package com.jbproject.jutopia.rest.controller.web.admin;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.service.CommCodeService;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            HttpServletRequest request, Model model
            , @PathVariable(value = "menuType", required = false) String menuType
            , MenuCudPayload payload
        ){
        // 현재 접속된 그룹 전송
        if(menuType == null){
            menuType = CommonConstatns.MENU_ROLE_USER;
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
    public RedirectView cudProc(HttpServletRequest request, Model model, MenuCudPayload payload){
        Long menuId = payload.getMenuId();
        System.out.println("payload : "+payload.getParentId());
        if(menuId == 0L){
            menuService.addMenu(payload);
        }else{
            menuService.modMenu(payload);
        }
        return new RedirectView("/admin/menu/main/"+payload.getMenuType());
    }


}
