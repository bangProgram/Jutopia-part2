package com.jbproject.jutopia.config.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.exception.ErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class SecurityUtils {

    public static JwtTokenInfo handleAuthentication(HttpServletRequest request) {
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



    public static void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) throws IOException, ServletException {
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
                Integer.toString(errorCode.getStatusCode()),
                errorCode.getErrorCode(),
                errorCode.getErrorMsg()
        );

        String body = new ObjectMapper().writeValueAsString(exceptionModel);
        System.out.println("JB test1 ");
        request.setAttribute("errorBody",body);
        request.setAttribute("errorStatus", HttpStatus.UNAUTHORIZED.value());
        request.setAttribute("errorContentType", MediaType.APPLICATION_JSON_VALUE);
        System.out.println("JB test2 ");
        request.getRequestDispatcher("/error/auth").forward(request, response);
        System.out.println("JB test3 ");
    }
}
