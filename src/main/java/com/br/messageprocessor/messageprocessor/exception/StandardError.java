package com.br.messageprocessor.messageprocessor.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError<T> {

    private Long timestamp;
    private Integer statusCode;
    private T error;
    private String message;
    private String path;
}
