package com.br.messageprocessor.messageprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MessageProcessError extends RuntimeException{

        public MessageProcessError() {
            super("Error processing message");
        }
}
