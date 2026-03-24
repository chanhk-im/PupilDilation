package com.chanhk.pupildilation.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String code;
    private String message;

    private ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
