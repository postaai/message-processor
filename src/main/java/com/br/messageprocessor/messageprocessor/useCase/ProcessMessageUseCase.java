package com.br.messageprocessor.messageprocessor.useCase;

import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;

public interface ProcessMessageUseCase {

    MessageUseCaseOutput process(MessageUseCaseInput messageUseCaseInput);
}
