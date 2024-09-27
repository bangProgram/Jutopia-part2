package com.jbproject.jutopia.config.security.filter;

import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class FilterAuthEntryPoint implements AuthenticationEntryPoint {

    private static final String RESPONSE_FORMAT =
            """
                    {
                        "error": {"request": "%s", "errorCode": "%s", "errorMsg": "%s"}
                    }
            """;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter()
                .write(
                        RESPONSE_FORMAT.formatted(
                                "",
                                authException.getMessage(),
                                authException.getMessage()
                        )
                );

    }
}
