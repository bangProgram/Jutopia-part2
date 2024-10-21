package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
    private final TokenProvider tokenProvider;
    private final AuthService authService;


    public AccessAuthFilter(
            ObjectMapper objectMapper, RequestMatcher defaultPermitAllPath, AuthService authService, TokenProvider tokenProvider
    ){
        this.objectMapper = objectMapper;
        this.defaultPermitAllPath = defaultPermitAllPath;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
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
            JwtTokenInfo jwtTokenInfo = SecurityUtils.handleAuthentication(request) ;
            String role = "VISITOR";  // 기본적으로 비로그인 상태는 'visitor'로 간주
            AccessJwtToken accessJwtToken = new AccessJwtToken();

            if(!jwtTokenInfo.getAccessToken().isEmpty()){
                try{
                    accessJwtToken = tokenProvider.getAccessAuthentication(jwtTokenInfo.getAccessToken());
                }catch (RuntimeException runtimeException){
                    if(!tokenProvider.validateToken(jwtTokenInfo.getRefreshToken(), JwtTokenConstants.REFRESH.getName())){
                        SecurityUtils.sendErrorResponse(request,response, SecurityErrorCode.JWT_AUTH_ERROR_07);
                        return;
                    }
                    accessJwtToken.setAuthenticated(false);
                    securityContextHolderStrategy.clearContext();

                    ErrorCode errorCode = switch (runtimeException) {
                        case SecurityException securityException -> SecurityErrorCode.JWT_AUTH_ERROR_01;
                        case MalformedJwtException malformedJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_01;
                        case ExpiredJwtException expiredJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_02;
                        case UnsupportedJwtException unsupportedJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_03;
                        case IllegalArgumentException illegalArgumentException -> SecurityErrorCode.JWT_AUTH_ERROR_04;
                        case SignatureException signatureException -> SecurityErrorCode.JWT_AUTH_ERROR_04;
                        default -> SecurityErrorCode.JWT_AUTH_ERROR_04;
                    };

                    SecurityUtils.sendErrorResponse(request,response,errorCode);
                    return;
                }
                role = accessJwtToken.getPrincipal().getRole(); // 인증된 사용자의 역할 가져오기
                accessJwtToken.setRole(role);

                System.out.println("JB authentication : "+role);
            }

            accessJwtToken.setAuthenticated(true);
            SecurityContext newContext = securityContextHolderStrategy.createEmptyContext();
            newContext.setAuthentication(accessJwtToken);
            securityContextHolderStrategy.setContext(newContext);

            List<RoleMenuResult> roleBasedUrls = authService.getRoleBasedWhiteList(role);

            System.out.println("roleBasedUrls : "+roleBasedUrls.stream().toList()+ " / "+requestURI);

            if ( (isAuthorization(requestURI, roleBasedUrls)) || role.equals(Role.SYSTEM.name()) ) {
                System.out.println("필터 들어옴");
                filterChain.doFilter(request, response);  // 허용된 URI일 경우 필터를 통과시킴
            } else {
                SecurityUtils.sendErrorResponse(request,response,SecurityErrorCode.FORBIDDEN_ERROR_02);
            }
        }
    }



    private boolean isAuthorization(String requestUrl, List<RoleMenuResult> roleBasedUrls) {

        String isCud = "N";
        String request = requestUrl;

        System.out.println("requestURL : "+requestUrl);
        if(requestUrl.contains("/cud")){
            isCud = "Y";
            request = requestUrl.replace("/cud", "");
        }
        System.out.println("requestURL : "+requestUrl);
        for(RoleMenuResult result : roleBasedUrls){
            System.out.println(result.getMenuUrl() + " / "+request);
            System.out.println(result.getIsCud() + " / "+isCud);

            if(result.getMenuUrl().equals(request) && result.getIsCud().equals(isCud)){
                return true;
            }
        }

        return false;
    }
}
