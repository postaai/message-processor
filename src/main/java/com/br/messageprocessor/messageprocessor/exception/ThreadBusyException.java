package com.br.messageprocessor.messageprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ThreadBusyException extends RuntimeException{
    public ThreadBusyException() {
        super("Thread is busy");
    }
}
