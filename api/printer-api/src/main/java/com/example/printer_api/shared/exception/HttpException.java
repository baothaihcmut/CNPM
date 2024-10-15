package com.example.printer_api.shared.exception;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException{
    private StatusCode statusCode;

    HttpException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode=statusCode;
    }
}
