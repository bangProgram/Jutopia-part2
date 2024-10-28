package com.jbproject.jutopia.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.SecurityErrorCode;
import com.jbproject.jutopia.constant.CommonErrorCode;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;


public class ExceptionProvider extends RuntimeException {

    private final ExceptionModel exceptionModel;
    private final int code;

    public ExceptionProvider(final ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ExceptionProvider(final ErrorCode errorCode, Throwable t) {
        super(errorCode.getErrorMsg(), t);
        this.exceptionModel = new ExceptionModel("", errorCode.getErrorCode(), errorCode.getErrorMsg());
        this.code = errorCode.getStatusCode();
    }

    public static void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode) throws IOException, ServletException {

        ExceptionModel exceptionModel = new ExceptionModel(
                Integer.toString(errorCode.getStatusCode()),
                errorCode.getErrorCode(),
                errorCode.getErrorMsg()
        );

        String body = new ObjectMapper().writeValueAsString(exceptionModel);
        request.setAttribute("errorBody", body);

        if(errorCode instanceof CommonErrorCode){
            request.getRequestDispatcher("/error/main").forward(request, response);
        }else if(errorCode instanceof SecurityErrorCode){
            request.getRequestDispatcher("/error/auth").forward(request, response);
        }
    }

    public ExceptionModel getError() {
        return exceptionModel;
    }

    public int getCode() {
        return code;
    }
}
