package com.br.messageprocessor.messageprocessor.useCase.impl;

import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.br.messageprocessor.messageprocessor.service.UserService;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessMessageUseCaseImpl implements ProcessMessageUseCase {

    private OpenAiGateway openAiGateway;
    private UserService userService;

    @Override
    public MessageUseCaseOutput process(MessageUseCaseInput messageUseCaseInput) {
        return null;
    }
}
