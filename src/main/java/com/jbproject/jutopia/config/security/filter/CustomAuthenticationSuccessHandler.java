package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.config.security.jwt.AccessJwtTokenBack;
import com.jbproject.jutopia.config.security.jwt.AccessJwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.Header;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessJwtTokenProvider accessJwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success Handler 동작 시작");
        if(authentication instanceof AccessJwtTokenBack){
            // JWT 토큰 생성
            System.out.println("AccessJwtToken Authentication 주입 완료");

            AccessJwtTokenBack.AccessJwtPrincipal principal = (AccessJwtTokenBack.AccessJwtPrincipal) authentication.getPrincipal();
            System.out.println("principal 을 통한 response Token 생성 "+ principal.getUserEmail() + " / "+principal.getRole());
            String accessToke = accessJwtTokenProvider.createCustomToken(
                    AccessJwtTokenBack.CustomClaims.builder()
                            .email(principal.getUserEmail())
                            .socialStatus(
                                    AccessJwtTokenBack.CustomClaims.SocialStatus.builder()
                                            .socialType(principal.getSocialType())
                                            .socialId(principal.getSocialId())
                                            .build()
                            )
                            .role(principal.getRole())
                            .build()
            );
            System.out.println("principal 을 통한 response Token 생성 "+ principal.getUserEmail() + " / "+principal.getRole());


            Cookie jwtCookie = new Cookie("X-Access-Token", accessToke);
            jwtCookie.setHttpOnly(true);  // XSS 방지
            jwtCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
            jwtCookie.setMaxAge(60 * 60);  // 쿠키 유효 기간 (1시간)
            response.addCookie(jwtCookie);

            Header jwtHeader = new Header("X-Access-Token", accessToke);
            response.addHeader("X-Access-Token", accessToke);

            // /main 경로로 리다이렉트
            System.out.println("성공합니다.");
            response.sendRedirect("/home");
        }
    }
}
