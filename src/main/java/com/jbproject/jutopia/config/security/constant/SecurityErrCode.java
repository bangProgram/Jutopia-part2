package com.jbproject.jutopia.config.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@AllArgsConstructor
@Getter
public enum SecurityErrCode {

    FORBIDDEN_ERROR(403,"Security.403.01", "Forbidden");

    private int responseCode;
    private String code;
    private String message;

}
