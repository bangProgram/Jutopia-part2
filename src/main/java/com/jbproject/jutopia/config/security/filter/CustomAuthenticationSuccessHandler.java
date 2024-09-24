package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.AccessJwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessJwtTokenProvider accessJwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(authentication instanceof AccessJwtToken){
            // JWT 토큰 생성
            AccessJwtToken.AccessJwtPrincipal principal = (AccessJwtToken.AccessJwtPrincipal) authentication.getPrincipal();
            accessJwtTokenProvider.createCustomToken(
                    AccessJwtToken.CustomClaims.builder()
                            .email(principal.getUserEmail())
                            .socialStatus(
                                    AccessJwtToken.CustomClaims.SocialStatus.builder()
                                            .socialType(principal.getSocialType())
                                            .socialId(principal.getSocialId())
                                            .build()
                            )
                            .role(principal.getRole())
                            .build()
            );


//        Cookie jwtCookie = new Cookie("JWT", token);
//        jwtCookie.setHttpOnly(true);  // XSS 방지
//        jwtCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
//        jwtCookie.setMaxAge(60 * 60);  // 쿠키 유효 기간 (1시간)
//        response.addCookie(jwtCookie);

            // /main 경로로 리다이렉트
            System.out.println("성공합니다.");
            response.sendRedirect("/main");
        }
    }
}
