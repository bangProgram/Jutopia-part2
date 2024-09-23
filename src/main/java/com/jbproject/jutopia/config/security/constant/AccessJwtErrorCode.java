package com.jbproject.jutopia.config.security.constant;

import com.jbproject.jutopia.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum AccessJwtErrorCode implements ErrorCode {

    INVALID_JWT_SIGNATURE(401,"AccessAuthError.401.01", "Forbidden"),
    EXPIRED_JWT_TOKEN(401,"AccessAuthError.401.02","접근이 허용되지 않은 경로입니다."),
    PARSING_ERROR(401,"AccessAuthError.401.02","접근이 허용되지 않은 경로입니다.")
    ;


    private int statusCode;
    private String errorCode;
    private String errorMsg;
}
