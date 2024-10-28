package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class AuthFilterConfig {
    @Autowired
    private AuthService authService;

    private final RequestMatcher defaultPermitAllPathMatcher;
    private final TokenProvider tokenProvider;

    @Bean("visitorBasedAuthList")
    public List<RoleMenuResult> visitorBasedAuthList(){
        List<RoleMenuResult> visitorBasedAuthList =  authService.getRoleBasedWhiteList(Role.VISITOR.name());
        System.out.println("JB Map<String, List<String>> roleBasedAuthList : "+visitorBasedAuthList);
        return visitorBasedAuthList;
    }

    @Bean("accessAuthFilterFactory")
    Supplier<AccessAuthFilter> accessAuthFilterFactory(){
        System.out.println("JB Security accountAuthFilterFactory");
        return () -> new AccessAuthFilter(
                defaultPermitAllPathMatcher,
                visitorBasedAuthList(),
                authService,
                tokenProvider
        );
    }
}
