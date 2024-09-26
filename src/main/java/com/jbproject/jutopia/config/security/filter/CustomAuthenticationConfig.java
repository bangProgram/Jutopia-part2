package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.jwt.AccessJwtTokenProvider;
import com.jbproject.jutopia.config.security.jwt.properties.AccessJwtProperties;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
public class CustomAuthenticationConfig {

    @Autowired
    public AuthService authService;

    @Bean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(TokenProvider tokenProvider
    ){
        return new CustomAuthenticationSuccessHandler(tokenProvider);
    }

    @Bean("customAuthenticationFilterFactory")
    Supplier<CustomAuthenticationFilter> customAuthenticationFilterFactory(
            ObjectMapper objectMapper,
            AuthService authService,
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler
    ){
        System.out.println("JB Security accountAuthFilterFactory");
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
                objectMapper,
                authService
        );
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

        return () -> customAuthenticationFilter;
    }
}
