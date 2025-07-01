package com.jbproject.jutopia.config.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import com.jbproject.jutopia.rest.dto.result.RoleMenuResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityUtils {

    private final AuthService authService;
    private static Map<String, List<RoleMenuResult>> roleMenu = new ConcurrentHashMap<>();

    @PostConstruct  // 빈 생성 후 자동 실행
    public void init() throws IOException {
        refreshRoleMenu();  // 초기 데이터 로딩
    }

    public void refreshRoleMenu(){
//        작업해야함
//        roleMenu = authService.getRoleBasedWhiteList("").stream().map(RoleMenuResult::getRoleId);
    }

    public static List<RoleMenuResult> menuListByRole(String role) {return roleMenu.get(role);}

    public static JwtTokenInfo handleAuthentication(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = "";
        String accessToken  = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(JwtTokenConstants.ACCESS.getKey())){
                    accessToken = cookie.getValue();
                }else if(cookie.getName().equals(JwtTokenConstants.REFRESH.getKey())){
                    refreshToken = cookie.getValue();
                }
            }
        }

        JwtTokenInfo jwtTokenInfo = JwtTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        System.out.println("accessToken 내용 : "+jwtTokenInfo.getAccessToken());
        System.out.println("refreshToken 내용 : "+jwtTokenInfo.getRefreshToken());
        return jwtTokenInfo;
    }

     public static void logSecurityContext(String message) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null) {
            System.out.println(message);
            System.out.println("Authentication class: " + authentication.getClass().getName());
            System.out.println("Is authenticated: " + authentication.isAuthenticated());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("SecurityContext hash: " + System.identityHashCode(context));
            System.out.println("authentication instanceof AccessJwtToken : " + (authentication instanceof AccessJwtToken));
            System.out.println("authentication instanceof RefreshJwtToken : " + (authentication instanceof RefreshJwtToken));
        } else {
            System.out.println(message + ": No authentication present");
        }
    }
}
