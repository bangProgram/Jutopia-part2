package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.jwt.properties.AccessJwtProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class AccessAuthFilterConfig {
    @Autowired
    private AuthService authService;
    private final RequestMatcher defaultPermitAllPathMatcher;
    private final AccessJwtProperties accessJwtProperties;

    @Bean("roleBasedAuthList")
    public Map<String, List<String>> roleBasedAuthList(){
        return authService.getAllRoleBasedUrls();
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
                roleBasedAuthList
        );
    }
}
