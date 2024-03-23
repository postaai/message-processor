package com.br.messageprocessor.messageprocessor.controller.impl;

import com.br.messageprocessor.messageprocessor.controller.MessageController;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageRequest;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageResponse;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.converter.ProcessMessageUseCaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageControllerImpl implements MessageController {

    private ProcessMessageUseCase processMessageUseCase;
    private ProcessMessageUseCaseConverter converter;

    @Override
    public MessageResponse processMessage(MessageRequest messageRequest) {

        var messageUseCaseOutput = processMessageUseCase.process(converter.toMessageUseCaseInput(messageRequest));


        return ResponseEntity.ok(converter.toMessageResponse(messageUseCaseOutput));
    }
}
