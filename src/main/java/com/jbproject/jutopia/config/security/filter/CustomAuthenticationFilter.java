package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import com.jbproject.jutopia.rest.entity.UserEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final AuthService authService;

    public CustomAuthenticationFilter(ObjectMapper objectMapper, AuthService authService) {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("JB Security CustomAuthenticationFilter");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        System.out.println("JB 사용자 입력 : "+ userId + " / "+password);
        UserEntity userDetail = authService.getUserInfoByUserid(userId);

        System.out.println("JB 사용자 정보 확인 pw : "+password + " / " +userDetail.getPassword() + " : " +authService.passwordMatcher(password,userDetail.getPassword()));
        if(authService.passwordMatcher(password,userDetail.getPassword())){
            System.out.println("JB 사용자 정보 확인 : "+userDetail.getEmail());
            AccessJwtToken authenticationToken = new AccessJwtToken();
            authenticationToken.setAccessJwtPrincipal(
                    AccessJwtToken.AccessJwtPrincipal.builder()
                            .id(userDetail.getId())
                            .build()
            );
            System.out.println("Token Authentication 발급 완료");

            authenticationToken.setRole(userDetail.getRole());
            authenticationToken.setAuthenticated(true);
            System.out.println("Token Authentication Role 주입 완료");

            return authenticationToken;
        }else{
            return null;
        }

    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, ErrorCode exception) throws IOException {

        ExceptionModel exceptionModel = new ExceptionModel(
                request.getRequestURI(),
                exception.getErrorCode(),
                exception.getErrorMsg()
        );

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(exceptionModel);
        response.getWriter().write(body);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
