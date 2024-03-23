package com.br.messageprocessor.messageprocessor.useCase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUseCaseInput {

    private String message;
    private String userId;
}
