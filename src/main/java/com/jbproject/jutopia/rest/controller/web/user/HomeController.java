package com.jbproject.jutopia.rest.controller.web.user;

import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/main")
    public String goMain(HttpServletRequest request,Model model) {

        return "/user/home/mainPage";
    }
}
