package com.jbproject.jutopia.constant;

import com.jbproject.jutopia.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    AUTHENTICATION_400_01(400, "AUTHENTICATION.400.01", "Invalid User Info.")

    ,COMMON_404_01(404, "COMMON.404.01", "잘못된 메뉴 타입입니다.")
    ,COMMON_404_02(404, "COMMON.404.02", "잘못된 권한 타입입니다.")
    ,MENU_404_01(404, "MENU.404.01", "해당 메뉴를 찾지 못했습니다.")
    ;

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
