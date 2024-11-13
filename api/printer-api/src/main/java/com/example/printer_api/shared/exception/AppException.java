package com.example.printer_api.shared.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private HttpStatus code;
    private String message;

    public AppException(StatusCode status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public HttpStatus getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
