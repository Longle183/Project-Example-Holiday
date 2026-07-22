package com.example.demo.exception;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class) public ResponseEntity<Map<String,String>> api(ApiException ex){return ResponseEntity.status(ex.getStatus()).body(Map.of("detail",ex.getMessage()));}
    @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,String>> fallback(Exception ex){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("detail","Lỗi hệ thống"));}
}
