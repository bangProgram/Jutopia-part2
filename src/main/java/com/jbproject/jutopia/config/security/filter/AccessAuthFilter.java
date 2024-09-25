package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.AccessJwtTokenBack;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AccessAuthFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final ObjectMapper objectMapper;
    private final RequestMatcher defaultPermitAllPath;
    private final Map<String,List<String>> roleBasedAuthList;


    public AccessAuthFilter(ObjectMapper objectMapper, RequestMatcher defaultPermitAllPath, Map<String,List<String>> roleBasedAuthList){
        this.objectMapper = objectMapper;
        this.defaultPermitAllPath = defaultPermitAllPath;
        this.roleBasedAuthList = roleBasedAuthList;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        // 인증에 상관 없이 pass 되어야 하는 경로
        if(defaultPermitAllPath.matches(request)){
            filterChain.doFilter(request, response);
        }
        // 모든 경로에 대한 인증및 인가 검증
        else{
            Authentication authentication = handleAuthentication(request) ;
            String role = "VISITOR";  // 기본적으로 비로그인 상태는 'visitor'로 간주

            if(authentication instanceof AccessJwtTokenBack){
                System.out.println("JB AccessJwtToken True");
            }else{
                System.out.println("JB AccessJwtToken False");
            }

            if (authentication != null && authentication.isAuthenticated()) {
                role = authentication.getAuthorities().iterator().next().getAuthority(); // 인증된 사용자의 역할 가져오기
                System.out.println("JB authentication : "+role);
            }

            Map<String, List<String>> roleBasedUrls = roleBasedAuthList;
            List<String> whiteList = roleBasedUrls.get(role);

            System.out.println("JB whiteList : "+whiteList);
            if (whiteList != null && whiteList.contains(requestURI) || role.equals(Role.SYSTEM.name())) {
                filterChain.doFilter(request, response);  // 허용된 URI일 경우 필터를 통과시킴
            } else {
                sendErrorResponse(request, response, SecurityErrorCode.FORBIDDEN_ERROR_02);
            }
        }
    }


    private Authentication handleAuthentication(HttpServletRequest request) {
        Authentication auth;
        Cookie[] cookies = request.getCookies();
        String token  = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("X-Access-Token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }else{
        }
        System.out.println("accessToken 내용 : "+token);
        return null;
    }


    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, ErrorCode exception) throws IOException {

        ExceptionModel exceptionModel = new ExceptionModel(
                request.getRequestURI(),
                exception.getErrorCode(),
                exception.getErrorMsg()
        );

        String body = objectMapper.writeValueAsString(exceptionModel);
        response.getWriter().write(body);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
