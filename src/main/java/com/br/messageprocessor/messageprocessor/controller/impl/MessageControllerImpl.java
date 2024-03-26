package com.br.messageprocessor.messageprocessor.controller.impl;

import com.br.messageprocessor.messageprocessor.controller.MessageController;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageRequest;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageResponse;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageControllerImpl implements MessageController {

    private final ProcessMessageUseCase processMessageUseCase;

    @Override
    public ResponseEntity<MessageResponse> processMessage(MessageRequest messageRequest) {

        System.out.println("------- mensagem chegou no controller: " + messageRequest.getMessage());

        var messageUseCaseOutput = processMessageUseCase.process(MessageUseCaseInput
                .builder()
                .message(messageRequest.getMessage())
                .userId(messageRequest.getUserId()).build());


        return ResponseEntity.ok(MessageResponse
                .builder()
                .message(messageUseCaseOutput.getMessage())
                .status(messageUseCaseOutput.getStatus())
                .build());
    }
}
