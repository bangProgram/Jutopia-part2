package com.jbproject.jutopia.config.security.constant;

import com.jbproject.jutopia.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements ErrorCode {

    FORBIDDEN_ERROR_01(403,"Security.403.01", "Forbidden"),
    FORBIDDEN_ERROR_02(403,"Security.403.02","접근이 허용되지 않은 경로입니다.");


    private int statusCode;
    private String errorCode;
    private String errorMsg;
}
