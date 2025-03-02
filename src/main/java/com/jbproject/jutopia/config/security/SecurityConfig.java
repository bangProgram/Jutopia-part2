package com.jbproject.jutopia.config.security;

import com.jbproject.jutopia.config.security.filter.FilterAuthEntryPoint;
import com.jbproject.jutopia.config.security.filter.AccessAuthFilter;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final String[] defaultPermitPath = {
            "/swagger-ui/**", "/v2/api-docs", "/swagger-resources",
            "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "swagger v3",
            "/v3/api-docs/**", "/swagger-ui/**", "/favicon.ico",
            "/auth/**","/error/**","/css/**", "/images/**", "/ajax/**",
            "/bootstrap/**"
    };

    // 기본 허용 path - request Matcher 추가
    @Bean
    RequestMatcher defaultPermitAllPathMatcher(){
        RequestMatcher[] matchers = Arrays.stream(defaultPermitPath)
                .map(AntPathRequestMatcher::new)
                .toList()
                .toArray(RequestMatcher[]::new);

        return RequestMatchers.anyOf(matchers);
    }

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security Cors 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("*"));
        config.setMaxAge(Duration.ofMinutes(5));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Spring Security 필터 Exception entry point
    @Bean
    FilterAuthEntryPoint filterAuthEntryPoint(){
        return new FilterAuthEntryPoint();
    }

    // Spring Security 인코딩 필터
    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        return filter;
    }

    @Bean
    TokenProvider tokenProvider() { return new TokenProvider(); }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity
            , @Qualifier("accessAuthFilterFactory")Supplier<AccessAuthFilter> accessAuthFilterFactory
//            , @Qualifier("refreshAuthFilterFactory")Supplier<RefreshAuthFilter> refreshAuthFilterFactory
            ) throws Exception {

        return httpSecurity
                // token 사용 방식, csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                // cors 허용 선언
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //컨트롤러의 예외처리를 담당하는 exception handler와는 다름.
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(filterAuthEntryPoint())
                )
                // security Form Login 미사용
                .formLogin(FormLoginConfigurer::disable)
                // Session Management 미사용
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests)->
                                authorizeRequests// HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                                        .requestMatchers(defaultPermitPath).permitAll()
                                        .anyRequest().authenticated() // 그 외 인증 없이 접근X
                )
                .addFilterBefore(characterEncodingFilter(), CsrfFilter.class)
                .addFilterBefore(accessAuthFilterFactory.get(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
