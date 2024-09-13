package com.jbproject.jutopia.config.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@AllArgsConstructor
public enum SecurityErrCode {

    FORBIDDEN_ERROR(403,"Security.403.01", "Forbidden");

    private int statusCode;
    private String errorCode;
    private String errorMsg;
}
