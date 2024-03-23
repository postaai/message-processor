package com.br.messageprocessor.messageprocessor.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ResourceExceptionHandler {

    public ResponseEntity<StandardError<String>> objectNotFound(ObjectNotFound e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError<>(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), e.getMessage(), "Resource not found", request.getRequestURI()));
    }
}
