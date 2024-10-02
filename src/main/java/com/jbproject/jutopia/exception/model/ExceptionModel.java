package com.jbproject.jutopia.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionModel {

    private String statusCode;
    private String errorCode;
    private String errorMsg;

    public ExceptionModel(String statusCode, String errorCode, String errorMsg){
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
