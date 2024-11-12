package com.example.printer_api.shared.exception;

import org.springframework.http.HttpStatus;

public enum StatusCode {
    USER_EXISTS(HttpStatus.NOT_FOUND, "User is exist"),
    EMAIL_NOT_EXIST(HttpStatus.NOT_FOUND, "Email not exist"),
    USER_INFO_EXIST(HttpStatus.CONFLICT, "User information has already been initialized"),
    DETAIL_REQUIRED(HttpStatus.BAD_REQUEST, "User detail is required");

    private HttpStatus code;
    private String message;

    StatusCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
