package com.jbproject.jutopia.rest.controller.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.rest.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Value("${jutopia.jwt.reset.duration}")
    private int jwtResetDuration;

    private final UserService userService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @GetMapping("/login")
    public String goLogin(HttpServletRequest request, Model model, LoginPayload loginPayload) {
        model.addAttribute("loginPayload", loginPayload);
        return "/user/auth/loginPage";
    }

    @PostMapping("/login")
    public RedirectView loginPorc(
            HttpServletRequest request, HttpServletResponse response, Model model, LoginPayload loginPayload
            ,RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("loginPayload", loginPayload);
        String userId = loginPayload.getUserId();
        String password = loginPayload.getPassword();

        UserEntity userEntity = authService.getUserInfoByUserid(userId);

        if(userEntity != null && authService.passwordMatcher(password,userEntity.getPassword())){
            System.out.println("JB 사용자 정보 확인 : "+userEntity.getEmail());
            generateToken(response, userEntity);

            return new RedirectView("/home/main");
        }else{
            redirectAttributes.addFlashAttribute("serverMessage",SecurityErrorCode.LOGIN_ERROR_01.getErrorMsg());
            return new RedirectView("/auth/login");
        }
    }

    @GetMapping("/refresh")
    public RedirectView refreshPorc(HttpServletRequest request, HttpServletResponse response, Model model) {
        JwtTokenInfo jwtTokenInfo = SecurityUtils.handleAuthentication(request);
        RefreshJwtToken token;

        try {
            token = tokenProvider.getRefreshAuthentication(jwtTokenInfo.getRefreshToken());
        }catch (RuntimeException exception){
            return new RedirectView("/auth/login");
        }

        RefreshJwtToken.RefreshJwtPrincipal principal = token.getPrincipal();

        UserEntity userEntity = userService.findById(principal.getId());
        generateToken(response, userEntity);


        return new RedirectView("/home/main");
    }

    @GetMapping("/signup")
    public String goSignup(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        model.addAttribute("signupPayload", signupPayload);
        return "/user/auth/signupPage";
    }

    @PostMapping("/signup")
    public RedirectView signupProc(HttpServletRequest request, Model model, SignupPayload signupPayload) {
        userService.addUser(signupPayload);
        System.out.println("/auth/signup process 종료");
        return new RedirectView("/auth/login");
    }

    @GetMapping("/logout")
    public RedirectView logoutProc(HttpServletResponse response) {
        securityContextHolderStrategy.clearContext();
        clearToken(response);

        return new RedirectView("/home/main");
    }

    private void generateToken(HttpServletResponse response, UserEntity userEntity){

        JwtTokenInfo jwtTokenInfo = tokenProvider.generateToken(
                AccessJwtToken.CustomClaims.builder()
                        .id(userEntity.getId())
                        .userId(userEntity.getUserId())
                        .userName(userEntity.getName())
                        .role(userEntity.getRole())
                        .build()
        );

        Cookie accessCookie = new Cookie("X-Access-Token", jwtTokenInfo.getAccessToken());
        accessCookie.setHttpOnly(true);  // XSS 방지
        accessCookie.setMaxAge(jwtResetDuration); // 쿠키 삭제 시간 - 브라우저가 켜져있는 상태에도 토큰 초기화 하도록 설정
        accessCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("X-Refresh-Token", jwtTokenInfo.getRefreshToken());
        refreshCookie.setHttpOnly(true);  // XSS 방지
        accessCookie.setMaxAge(jwtResetDuration); // 쿠키 삭제 시간 - 브라우저가 켜져있는 상태에도 토큰 초기화 하도록 설정
        refreshCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
        response.addCookie(refreshCookie);
    }

    private void clearToken(HttpServletResponse response){

        Cookie accessCookie = new Cookie("X-Access-Token", null);
        accessCookie.setMaxAge(0);
        accessCookie.setHttpOnly(true);  // XSS 방지
        accessCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("X-Refresh-Token", null);
        refreshCookie.setHttpOnly(true);  // XSS 방지
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
        response.addCookie(refreshCookie);
    }


}
