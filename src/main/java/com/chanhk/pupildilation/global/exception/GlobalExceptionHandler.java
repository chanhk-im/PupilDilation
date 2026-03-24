package com.chanhk.pupildilation.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // business exception 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e) {
        return createResponseEntity(e.getErrorCode());
    }

    // @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return createResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    // 나머지 예상 못한 예외 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return createResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(ErrorResponse.of(errorCode), errorCode.getHttpStatus());
    }
}
