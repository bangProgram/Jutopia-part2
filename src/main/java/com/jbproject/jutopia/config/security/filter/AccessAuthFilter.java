package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.auth.service.AuthService;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.dto.result.RoleMenuResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AccessAuthFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final List<RoleMenuResult> visitorBasedAuthList;
    private final RequestMatcher defaultPermitAllPath;
    private final TokenProvider tokenProvider;
    private final AuthService authService;


    public AccessAuthFilter(
            RequestMatcher defaultPermitAllPath, List<RoleMenuResult> visitorBasedAuthList, AuthService authService, TokenProvider tokenProvider
    ){
        this.defaultPermitAllPath = defaultPermitAllPath;
        this.visitorBasedAuthList = visitorBasedAuthList;
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
            /*
                기본적으로 비로그인 상태는 'visitor'로 간주
                roleBasedUrls 인가 체크 또한 visitor 기준 세팅
            */
            String role = "VISITOR";
            List<RoleMenuResult> roleBasedUrls = visitorBasedAuthList;


            AccessJwtToken accessJwtToken = new AccessJwtToken();

            // Access Token 이 존재할 경우
            if(!jwtTokenInfo.getAccessToken().isEmpty()){
                try{
                    accessJwtToken = tokenProvider.getAccessAuthentication(jwtTokenInfo.getAccessToken());
                }catch (RuntimeException runtimeException){
                    if(!tokenProvider.validateToken(jwtTokenInfo.getRefreshToken(), JwtTokenConstants.REFRESH.getName())){
                        ExceptionProvider.sendErrorResponse(request,response, SecurityErrorCode.JWT_AUTH_ERROR_07);
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

                    ExceptionProvider.sendErrorResponse(request,response,errorCode);
                    return;
                }
                role = accessJwtToken.getPrincipal().getRole(); // 인증된 사용자의 역할 가져오기
                accessJwtToken.setRole(role);

                // Access Token 이 있을경우 Role 에 해당하는 인가 경로 변경
                System.out.println("롤체크 하기위한 메뉴 호출");
                roleBasedUrls = SecurityUtils.menuListByRole(role) == null ? visitorBasedAuthList : SecurityUtils.menuListByRole(role);
                System.out.println("roleBasedUrls : "+roleBasedUrls);

                accessJwtToken.setAuthenticated(true);
                SecurityContext newContext = securityContextHolderStrategy.createEmptyContext();
                newContext.setAuthentication(accessJwtToken);
                securityContextHolderStrategy.setContext(newContext);
            }
            // Access Token 이 존재하지 않을경우 VISITOR 로 간주하고 인증 및 인가 진행
            else{
                log.info("Token 없음 : Visitor 인증 시도");
                accessJwtToken.setAccessJwtPrincipal(
                        AccessJwtToken.AccessJwtPrincipal.builder()
                                .role(Role.VISITOR.name())
                                .build()
                );

                // Access Token 이 없을 경우 VISITOR 로써 인증
                accessJwtToken.setAuthenticated(true);
                SecurityContext newContext = securityContextHolderStrategy.createEmptyContext();
                newContext.setAuthentication(accessJwtToken);
                securityContextHolderStrategy.setContext(newContext);
            }


            if ( role.equals(Role.SYSTEM.name()) ) {
                log.info("System 인가 허용");
                filterChain.doFilter(request, response);
            } else {
                log.info("roleBasedUrls : "+roleBasedUrls.stream().toList()+ " / "+requestURI);

                if(isAuthorization(requestURI, roleBasedUrls)){
                    log.info("인가 체크 : true");
                    filterChain.doFilter(request, response); // 허용된 URI일 경우 필터를 통과시킴
                }else{
                    log.error("인가 체크 : false");
                    ExceptionProvider.sendErrorResponse(request,response,SecurityErrorCode.FORBIDDEN_ERROR_02);
                }
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

            // 1. 현재 동작이 CUD 일 경우
            if(isCud.equals("Y")){
                /*
                    1-1. 사용자 메뉴 쓰기권한 확인
                    쓰기권한 확인 시 메뉴 접근권한 + 쓰기권한 함께 확인
                    roleBasedUrls.getMenuUrl = requestUrl 체크
                    roleBasedUrls.getIsCud = "Y" 체크

                    20241024 변경
                    cud 로직일 경우 메뉴에 대한 권한 체크만 확인하는 것으로 변경
                */
                System.out.println("url Check : "+request + " / "+result.getMenuUrl());
                if( request.contains(result.getMenuUrl())
//                        && result.getIsCud().equals("Y")
                ){
                    return true;
                }
            }
            // 2. 현재 동작이 CUD 가 아닐경우 메뉴 접근권한 체크
            else{
                /*
                    2-1. 사용자 메뉴 접근권한 확인
                    roleBasedUrls.getMenuUrl = requestUrl 체크
                */
                System.out.println("url Check : "+request + " / "+result.getMenuUrl());
                if(request.contains(result.getMenuUrl())){
                    return true;
                }
            }
        }

        return false;
    }
}
