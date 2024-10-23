package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

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
                            .userId(userDetail.getUserId())
                            .userName(userDetail.getName())
                            .role(userDetail.getRole())
                            .build()
            );
            System.out.println("Token Authentication 발급 완료");

            authenticationToken.setRole(userDetail.getRole());
            System.out.println("Token Authentication Role 주입 완료");

            List<RoleMenuResult> roleBasedUrls = authService.getRoleBasedWhiteList(userDetail.getRole());
            authenticationToken.setRoleBasedUrls(roleBasedUrls);

            Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();
            System.out.println("authentication 존재여부11 : "+(authentication != null) + " / "+ authentication);

            authenticationToken.setAuthenticated(true);
            SecurityContext newContext = securityContextHolderStrategy.createEmptyContext();
            newContext.setAuthentication(authenticationToken);
            securityContextHolderStrategy.setContext(newContext);
            System.out.println("authentication 존재여부22 : "+(authentication != null) + " / "+ authentication);
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
