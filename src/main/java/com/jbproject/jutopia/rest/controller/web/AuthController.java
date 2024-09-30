package com.jbproject.jutopia.rest.controller.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.rest.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final UserService userService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public RedirectView loginPorc(HttpServletRequest request, HttpServletResponse response, Model model, LoginPayload loginPayload) {
        model.addAttribute("loginPayload", loginPayload);
        String userId = loginPayload.getUserId();
        String password = loginPayload.getPassword();

        UserEntity userDetail = authService.getUserInfo(userId);

        if(authService.passwordMatcher(password,userDetail.getPassword())){
            System.out.println("JB 사용자 정보 확인 : "+userDetail.getEmail());

            JwtTokenInfo jwtTokenInfo = tokenProvider.generateToken(
                    AccessJwtToken.CustomClaims.builder()
                            .id(userDetail.getId())
                            .userId(userDetail.getUserId())
                            .userName(userDetail.getName())
                            .role(userDetail.getRole())
                            .build()
            );

            Cookie accessCookie = new Cookie("X-Access-Token", jwtTokenInfo.getAccessToken());
            accessCookie.setHttpOnly(true);  // XSS 방지
            accessCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
            response.addCookie(accessCookie);

            Cookie refreshCookie = new Cookie("X-Refresh-Token", jwtTokenInfo.getRefreshToken());
            refreshCookie.setHttpOnly(true);  // XSS 방지
            refreshCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
            response.addCookie(refreshCookie);

            return new RedirectView("/home");
        }else{
            return new RedirectView("/auth/login");
        }
    }

    @GetMapping("/auth/signup")
    public String goSignup(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        model.addAttribute("signupPayload", signupPayload);
        return "/user/auth/signupPage";
    }

    @PostMapping("/auth/signup")
    public RedirectView signupProc(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        userService.addUser(signupPayload);
        System.out.println("/auth/signup process 종료");
        return new RedirectView("/auth/login");
    }
}
