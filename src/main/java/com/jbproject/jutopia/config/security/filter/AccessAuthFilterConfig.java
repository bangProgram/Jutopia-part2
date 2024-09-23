package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

//@Configuration
public class AccessAuthFilterConfig {
//    @Autowired
//    private AuthService authService;
    private final String[] defaultPermitPath = {
            "/swagger-ui/**", "/v2/api-docs", "/swagger-resources",
            "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "swagger v3",
            "/v3/api-docs/**", "/swagger-ui/**", "/favicon.ico",
            "/auth/**"
    };

//    @Bean("defaultPermitAllPathMatcher")
    RequestMatcher defaultPermitAllPathMatcher(){
        System.out.println("JB Security defaultPermitAllPathMatcher");
        RequestMatcher[] matchers = Arrays.stream(defaultPermitPath)
                .map(AntPathRequestMatcher::new)
                .toList()
                .toArray(RequestMatcher[]::new);

        return RequestMatchers.anyOf(matchers);
    }

//    @Bean("roleBasedAuthList")
//    public Map<String, List<String>> roleBasedAuthList(){
//        System.out.println("JB Security roleBasedAuthList");
//        return authService.getAllRoleBasedUrls();
//    }

//    @Bean("accessAuthFilterFactory")
//    Supplier<AccessAuthFilter> accountAuthFilterFactory(
//            ObjectMapper objectMapper,
//            @Qualifier("defaultPermitAllPathMatcher") RequestMatcher defaultPermitAllPathMatcher,
//            @Qualifier("roleBasedAuthList") Map<String, List<String>> roleBasedAuthList
//    ){
//        System.out.println("JB Security accountAuthFilterFactory");
//        return () -> new AccessAuthFilter(
//                objectMapper,
//                defaultPermitAllPathMatcher,
//                roleBasedAuthList
//        );
//    }
}
