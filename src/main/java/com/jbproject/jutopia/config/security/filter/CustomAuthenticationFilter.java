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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

//    @Autowired
    private AuthService authService;

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("JB Security CustomAuthenticationFilter");
        LoginPayload payload = new ObjectMapper().readValue(request.getInputStream(), LoginPayload.class);
        UserEntity userInfo = authService.getUserInfo(payload);
        AccessJwtToken token = new AccessJwtToken(
                AccessJwtPrincipal.builder()
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
}
