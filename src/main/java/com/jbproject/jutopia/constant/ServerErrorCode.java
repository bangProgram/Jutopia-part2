package com.jbproject.jutopia.constant;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ServerErrorCode implements CommonError {

    POST_404_01(404, "POST.404.01", "해당 게시글을 찾지 못했습니다.")
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
