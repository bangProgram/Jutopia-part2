package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
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

    @Bean("roleBasedAuthList")
    public Map<String, List<String>> roleBasedAuthList(){
        Map<String, List<String>> roleBasedAuthList =  authService.getAllRoleBasedUrls();
        System.out.println("JB Map<String, List<String>> roleBasedAuthList : "+roleBasedAuthList);
        return roleBasedAuthList;
    }

    @Bean("accessAuthFilterFactory")
    Supplier<AccessAuthFilter> accessAuthFilterFactory(
            ObjectMapper objectMapper,
            @Qualifier("roleBasedAuthList") Map<String, List<String>> roleBasedAuthList
    ){
        System.out.println("JB Security accountAuthFilterFactory");
        return () -> new AccessAuthFilter(
                objectMapper,
                defaultPermitAllPathMatcher,
                roleBasedAuthList,
                tokenProvider
        );
    }

    @Bean("refreshAuthFilterFactory")
    Supplier<RefreshAuthFilter> refreshAuthFilterFactory(
            ObjectMapper objectMapper
    ){
        System.out.println("JB Security accountAuthFilterFactory");
        return () -> new RefreshAuthFilter(
                objectMapper,
                new AntPathRequestMatcher("/auth/refresh","POST"),
                tokenProvider
        );
    }
}
