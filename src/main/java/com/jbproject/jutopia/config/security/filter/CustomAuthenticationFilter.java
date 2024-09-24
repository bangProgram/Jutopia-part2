package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.jwt.AccessJwtPrincipal;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final AuthService authService;

    public CustomAuthenticationFilter(ObjectMapper objectMapper, AuthService authService) {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("JB Security CustomAuthenticationFilter");
        String email = request.getParameter("username");
        System.out.println("JB : "+email);
        String password = request.getParameter("password");
        System.out.println("test1 : "+password);

        LoginPayload payload = new LoginPayload();
        payload.setEmail(email);
        payload.setPassword(password);

        UserEntity userInfo = authService.getUserInfo(payload);

        AccessJwtToken token = new AccessJwtToken(
                AccessJwtToken.AccessJwtPrincipal.builder()
                        .userEmail(userInfo.getEmail())
                        .userName(userInfo.getName())
                        .age(userInfo.getAge())
                        .socialType(userInfo.getSocialType())
                        .socialId(userInfo.getSocialId())
                        .role(userInfo.getRole())
                        .build()
        );

        token.setRole(userInfo.getRole());

        return token;
    }

//    private Authentication handleAuthentication(HttpServletRequest request, String requestURI) {
//        Authentication auth;
//
//        String accessToken = request.getHeader("X-Access-Authorization");
//
//
//        return auth;
//    }
}
