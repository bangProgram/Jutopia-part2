package com.jbproject.jutopia.config.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessJwtTokenProvider accessJwtTokenProvider;
    private final

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // JWT 토큰 생성
        String token = accessJwtTokenProvider.createCustomToken();

        // 쿠키에 JWT 추가
        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true);  // XSS 방지
        jwtCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
        jwtCookie.setMaxAge(60 * 60);  // 쿠키 유효 기간 (1시간)
        response.addCookie(jwtCookie);

        // /main 경로로 리다이렉트
        response.sendRedirect("/main");
    }
}
