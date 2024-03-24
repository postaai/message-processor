package com.br.messageprocessor.messageprocessor.useCase;

import com.br.messageprocessor.messageprocessor.useCase.dto.ValidationMessageUseCaseInput;

public interface ValidateMessageUseCase {
    
    Boolean execute(ValidationMessageUseCaseInput input);


}
