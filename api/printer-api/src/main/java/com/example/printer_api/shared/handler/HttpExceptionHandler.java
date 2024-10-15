// package com.example.printer_api.shared.handler;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import com.example.printer_api.shared.entity.CustomReponse;
// import com.example.printer_api.shared.exception.HttpException;

// @RestControllerAdvice
// public class HttpExceptionHandler {
//     @ExceptionHandler(HttpException.class)
//     public ResponseEntity<CustomReponse<Object>> handleHttpException(HttpException e) {
//         return ResponseEntity.status(e.getStatusCode().getCode()).body()
//     }
// }
