package com.jbproject.jutopia.rest.controller.web;


import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.rest.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/login")
    public String goLogin(HttpServletRequest request, Model model, LoginPayload loginPayload) {
        /*
        model.addAttribute("testParam1","testParam1");
        model.addAttribute("test1","test1");
        model.addAttribute("test2","test2");

        Map<String, String> test = new HashMap<>();
        test.put("testTitle", "testTitle Text");
        test.put("testDetail", "testDetail Text");

        model.addAttribute("test",test);
        */
        model.addAttribute("loginPayload", loginPayload);
        return "/user/auth/loginPage";
    }

    @PostMapping("/auth/login")
    public String loginProc(HttpServletRequest request, Model model, LoginPayload loginPayload) {
        model.addAttribute("loginPayload", loginPayload);
        return "/user/auth/loginPage";
    }

    @GetMapping("/auth/signup")
    public String goSignup(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        model.addAttribute("signupPayload", signupPayload);
        return "/user/auth/signupPage";
    }

    @PostMapping("/auth/signup")
    public RedirectView signupProc(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        authService.addUser(signupPayload);
        System.out.println("signup/proc 종료");
        return new RedirectView("/auth/login");
    }
}
