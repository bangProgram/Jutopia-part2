package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.constant.SecurityErrCode;
import com.jbproject.jutopia.config.security.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RolebaseAuthFilter extends OncePerRequestFilter {

    private final RequestMatcher defaultPermitAllPath;
    @Autowired
    private Map<String, List<String>> roleBasedWhiteList;

    public RolebaseAuthFilter(RequestMatcher defaultPermitAllPath){
        this.defaultPermitAllPath = defaultPermitAllPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if(defaultPermitAllPath.matches(request)){
            filterChain.doFilter(request, response);
        }else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String role = "VISITOR";  // 기본적으로 비로그인 상태는 'visitor'로 간주

            if (authentication != null && authentication.isAuthenticated()) {
                role = authentication.getAuthorities().iterator().next().getAuthority();  // 인증된 사용자의 역할 가져오기
            }

            Map<String, List<String>> roleBasedUrls = roleBasedWhiteList;
            List<String> whiteList = roleBasedUrls.get(role);

            System.out.println("JB whiteList : "+whiteList);
            if (whiteList != null && whiteList.contains(requestURI)) {
                filterChain.doFilter(request, response);  // 허용된 URI일 경우 필터를 통과시킴
            } else {
                response.sendError(SecurityErrCode.FORBIDDEN_ERROR_02.getStatusCode(), SecurityErrCode.FORBIDDEN_ERROR_02.getErrorMsg());
            }
        }
    }
}
