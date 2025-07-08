package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.rest.dto.result.RoleMenuResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class AuthFilterConfig {
    @Autowired
    private AuthService authService;

    private final RequestMatcher defaultPermitAllPathMatcher;
    private final TokenProvider tokenProvider;

    @Bean("accessAuthFilterFactory")
    Supplier<AccessAuthFilter> accessAuthFilterFactory(){
        System.out.println("JB Security accountAuthFilterFactory");
        return () -> new AccessAuthFilter(
                defaultPermitAllPathMatcher,
                authService,
                tokenProvider
        );
    }
}
