package com.srb.srb.exception.handler;

import com.srb.srb.exception.ErrorResponse;
import com.srb.srb.exception.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 400 code : 잘못된 요청
     * @return
     */
    @ExceptionHandler( HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> badRequest() {
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatus().value())
                .body(new ErrorResponse(ErrorCode.BAD_REQUEST));
    }

    /**
     * 500 code : 통신 오류
     * @return
     */
    @ExceptionHandler( Exception.class)
    public ResponseEntity<ErrorResponse> internalError() {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
