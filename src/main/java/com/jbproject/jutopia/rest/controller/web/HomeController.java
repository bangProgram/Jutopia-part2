package com.jbproject.jutopia.rest.controller.web;

import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String goHome(HttpServletRequest request,Model model, LoginPayload loginPayload) {

        model.addAttribute("loginPayload", loginPayload);
        return "/user/auth/loginPage";
    }
}
