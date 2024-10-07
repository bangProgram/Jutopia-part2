package com.jbproject.jutopia.rest.controller.web.admin;

import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/main")
    public String goMain(HttpServletRequest request, Model model){
        return "/admin/menu/mainPage";
    }

    @GetMapping("/cud")
    public String goCud(HttpServletRequest request, Model model, MenuCudPayload payload){
        model.addAttribute("menuCudPayload", payload);
        return "/admin/menu/cudPage";
    }


    @PostMapping("/cud")
    public RedirectView cudProc(HttpServletRequest request, Model model, MenuCudPayload payload){
        Long menuId = payload.getMenuId();
        if(menuId == 0L){
            menuService.addMenu(payload);
        }else{
            menuService.modMenu(payload);
        }
        return new RedirectView("/admin/menu/cud");
    }
}
