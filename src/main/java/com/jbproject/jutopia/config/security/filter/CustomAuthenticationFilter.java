package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthService authService;

    CustomAuthenticationFilter(AuthenticationManager authenticationManager, FilterAuthEntryPoint filterAuthEntryPoint){
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = filterAuthEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try {
            LoginPayload payload = new ObjectMapper().readValue(request.getInputStream(), LoginPayload.class);

            UserEntity userInfo = authService.getUserInfo(payload);
        }catch (AuthenticationException exception){
            log.info("Failed Refresh Security Filter : {}",exception.getMessage());
            authenticationEntryPoint.commence(request,response,exception);
        }

    }
}
