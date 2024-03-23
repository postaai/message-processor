package com.br.messageprocessor.messageprocessor.useCase;

import com.br.messageprocessor.messageprocessor.useCase.dto.ValidationMessageUseCaseInput;

public interface ValidationMessageUseCase {
    
    Boolean execute(ValidationMessageUseCaseInput input);


}
