package com.example.printer_api.shared.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.printer_api.shared.exception.HttpException;
import com.example.printer_api.shared.response.CustomReponse;
import com.example.printer_api.shared.response.ResponseFactory;
import org.springframework.http.converter.HttpMessageConversionException;

@RestControllerAdvice
public class HttpExceptionHandler {
    @Autowired
    private ResponseFactory  responseFactory;

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<CustomReponse> handleHttpException(HttpException e) {
        return ResponseEntity.status(e.getStatusCode().getCode()).body(this.responseFactory.initResponse(false, e.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomReponse> handleHttpException(RuntimeException e) {
        System.err.println(e.getClass());
        return ResponseEntity.status(500).body(this.responseFactory.initResponse(false, "Internal error", null));
    }
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<CustomReponse> handleBadRequest(HttpMessageConversionException e) {
        return ResponseEntity.status(400).body(this.responseFactory.initResponse(false,"Wrong Data type",null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomReponse> handleDTOException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(400).body(this.responseFactory.initResponse(false,"Unvalid Body",errorMap));
    }

    
}
