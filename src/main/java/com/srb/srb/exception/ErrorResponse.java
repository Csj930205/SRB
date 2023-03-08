package com.srb.srb.exception;

import com.srb.srb.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    /**
     * 에러 발생 시간
     */
    private final LocalDateTime timestamp = LocalDateTime.now();

    /**
     * 상태 코드
     */
    private final int code;

    /**
     * 메세지
     */
    private final String message;

    /**
     * 결과 값
     */
    private final String result;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
        this.result = errorCode.getResult();
    }

}
