package com.jbproject.jutopia.exception;

import com.jbproject.jutopia.exception.model.ExceptionModel;
import lombok.RequiredArgsConstructor;


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

    public ExceptionModel getError() {
        return exceptionModel;
    }

    public int getCode() {
        return code;
    }
}
