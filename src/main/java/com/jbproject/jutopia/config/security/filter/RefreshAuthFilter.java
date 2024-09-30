package com.jbproject.jutopia.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import com.jbproject.jutopia.config.security.provider.TokenProvider;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class RefreshAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AntPathRequestMatcher requestMatcher;
    private final TokenProvider tokenProvider;

    public RefreshAuthFilter(ObjectMapper objectMapper, AntPathRequestMatcher requestMatcher, TokenProvider tokenProvider){
        this.objectMapper = objectMapper;
        this.requestMatcher = requestMatcher;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!requestMatcher.matches(request)){
            doFilter(request,response,filterChain);
        }else{
            JwtTokenInfo jwtTokenInfo = handleAuthentication(request);

            try{
                String token = jwtTokenInfo.getRefreshToken();

            }catch (RuntimeException exception){
                sendErrorResponse(request, response, exception);
            }
        }
    }

    private JwtTokenInfo handleAuthentication(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = "";
        String accessToken  = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(JwtTokenConstants.ACCESS.getKey())){
                    accessToken = cookie.getValue();
                }else if(cookie.getName().equals(JwtTokenConstants.REFRESH.getKey())){
                    refreshToken = cookie.getValue();
                }
            }
        }

        JwtTokenInfo jwtTokenInfo = JwtTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        System.out.println("accessToken 내용 : "+jwtTokenInfo.getAccessToken());
        System.out.println("refreshToken 내용 : "+jwtTokenInfo.getRefreshToken());
        return jwtTokenInfo;
    }



    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) throws IOException {
        ErrorCode errorCode = switch (exception) {
            case SecurityException securityException -> SecurityErrorCode.JWT_AUTH_ERROR_01;
            case MalformedJwtException malformedJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_01;
            case ExpiredJwtException expiredJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_02;
            case UnsupportedJwtException unsupportedJwtException -> SecurityErrorCode.JWT_AUTH_ERROR_03;
            case IllegalArgumentException illegalArgumentException -> SecurityErrorCode.JWT_AUTH_ERROR_04;
            case SignatureException signatureException -> SecurityErrorCode.JWT_AUTH_ERROR_04;
            default -> SecurityErrorCode.JWT_AUTH_ERROR_06;
        };

        ExceptionModel exceptionModel = new ExceptionModel(
                request.getRequestURI(),
                errorCode.getErrorCode(),
                errorCode.getErrorMsg()
        );

        String body = objectMapper.writeValueAsString(exceptionModel);
        response.getWriter().write(body);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
