package com.jbproject.jutopia.rest.controller.web.user;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class UserPostController {

    @GetMapping("/main")
    public String goBoard(HttpServletRequest request, Model model) {
        return "/user/post/mainPage";
    }
}
