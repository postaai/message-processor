package com.br.messageprocessor.messageprocessor.useCase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageUseCaseOutput {

    private String status;
    private String message;

}
