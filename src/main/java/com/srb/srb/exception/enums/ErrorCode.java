package com.srb.srb.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 400 code
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "fail") ,

    /**
     * 404 code
     */
    NOT_FOUND( HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다.", "fail"),

    /**
     * 404 code(Update)
     */
    UPDATE_NOT( HttpStatus.NOT_FOUND, "업데이트 실패. 데이터를 찾을 수 없거나 잘못된 요청입니다.", "fail"),

    /**
     * 409 code(Insert)
     */
    INSERT_NOT( HttpStatus.CONFLICT, "중복된 값이 존재합니다.", "fail"),

    /**
     * 500 code
     */
    INTERNAL_SERVER_ERROR( HttpStatus.INTERNAL_SERVER_ERROR, "통신 오류", "fail");

    private final HttpStatus httpStatus;

    private final String message;

    private final String result;

}
