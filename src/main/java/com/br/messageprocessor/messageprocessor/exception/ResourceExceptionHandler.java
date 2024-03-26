package com.br.messageprocessor.messageprocessor.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {


    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String ERROR_PROCESSING_MESSAGE = "Error processing message";
    public static final String THREAD_IS_BUSY = "Thread is busy";

    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<StandardError<String>> objectNotFound(ObjectNotFound e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError<>(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                OBJECT_NOT_FOUND,
                e.getMessage(),
                request.getRequestURI()));
    }

    @ExceptionHandler(MessageProcessError.class)
    public ResponseEntity<StandardError<String>> messageProcessError(MessageProcessError e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError<>(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                ERROR_PROCESSING_MESSAGE,
                e.getMessage(),
                request.getRequestURI()));
    }

    @ExceptionHandler(ThreadBusyException.class)
    public ResponseEntity<StandardError<String>> threadBusyException(ThreadBusyException e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.TOO_EARLY).body(new StandardError<>(
                System.currentTimeMillis(),
                HttpStatus.TOO_EARLY.value(),
                THREAD_IS_BUSY,
                e.getMessage(),
                request.getRequestURI()));
    }

}
