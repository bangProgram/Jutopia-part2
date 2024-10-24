package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import com.jbproject.jutopia.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // /main 경로로 리다이렉트
        System.out.println("실패합니다.");
        SecurityUtils.sendErrorResponse( request,  response, SecurityErrorCode.LOGIN_ERROR_01);
    }
}
