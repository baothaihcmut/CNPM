package com.example.printer_api.shared.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.printer_api.shared.exception.AppException;
import com.example.printer_api.shared.response.CustomReponse;
import com.example.printer_api.shared.response.ResponseFactory;

@RestControllerAdvice
public class HttpExceptionHandler {
    @Autowired
    private ResponseFactory responseFactory;

    private Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomReponse> handleHttpException(RuntimeException e) {
        logger.error("Internal error", e);
        return ResponseEntity.status(500).body(this.responseFactory.initResponse(false, "Internal error", null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomReponse> handleDTOException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(400).body(this.responseFactory.initResponse(false, "Unvalid Body", errorMap));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<CustomReponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getCode()).body(this.responseFactory.initResponse(false, e.getMessage(), null));
    }
}
