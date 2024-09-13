package com.jbproject.jutopia.constant;

import com.jbproject.jutopia.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ErrorCodeConstants implements ErrorCode {

    AUTHENTICATION_400_01(400, "AUTHENTICATION.400.01", "Invalid User Info.");

    private int statusCode;
    private String errorCode;
    private String errorMsg;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
