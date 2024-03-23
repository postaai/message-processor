package com.br.messageprocessor.messageprocessor.exception;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StandardError<T> {

    private Timestamp timestamp;
    private Integer statusCode;
    private T error;
    private String message;
    private String path;
}
