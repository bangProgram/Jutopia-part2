package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
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

//    private final AccessJwtTokenProvider accessJwtTokenProvider;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success Handler 동작 시작");
        if(authentication instanceof AccessJwtToken accessJwtToken){
            // JWT 토큰 생성
            System.out.println("AccessJwtToken Authentication 주입 완료");
/*

            JwtTokenInfo jwtTokenInfo = tokenProvider.generateToken(accessJwtToken);

            Cookie accessCookie = new Cookie("X-Access-Token", jwtTokenInfo.getAccessToken());
            accessCookie.setHttpOnly(true);  // XSS 방지
            accessCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
//            accessCookie.setMaxAge((int) tokenProvider.getExpirationTime(JwtTokenConstants.ACCESS.getName()));  // 쿠키 유효 기간 (임시 1분)
            response.addCookie(accessCookie);

            Cookie refreshCookie = new Cookie("X-Refresh-Token", jwtTokenInfo.getRefreshToken());
            refreshCookie.setHttpOnly(true);  // XSS 방지
            refreshCookie.setPath("/");  // 모든 경로에서 쿠키를 사용할 수 있도록 설정
//            refreshCookie.setMaxAge((int) tokenProvider.getExpirationTime(JwtTokenConstants.REFRESH.getName()));  // 쿠키 유효 기간 (10시간)
            response.addCookie(refreshCookie);

*/

            // /main 경로로 리다이렉트
            System.out.println("성공합니다.");
            response.sendRedirect("/home");
        }
    }
}
