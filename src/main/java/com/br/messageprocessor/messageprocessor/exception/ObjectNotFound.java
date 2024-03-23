package com.br.messageprocessor.messageprocessor.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFound extends RuntimeException{


    public ObjectNotFound() {
        super("Object not found");
    }
}
