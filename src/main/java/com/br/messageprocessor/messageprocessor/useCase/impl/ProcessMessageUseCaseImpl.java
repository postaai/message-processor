package com.br.messageprocessor.messageprocessor.useCase.impl;

import com.br.messageprocessor.messageprocessor.entity.UserEntity;
import com.br.messageprocessor.messageprocessor.exception.ObjectNotFound;
import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.br.messageprocessor.messageprocessor.service.UserService;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;
import com.theokanning.openai.threads.Thread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessMessageUseCaseImpl implements ProcessMessageUseCase {

    private OpenAiGateway openAiGateway;
    private UserService userService;

    @Override
    public MessageUseCaseOutput process(MessageUseCaseInput messageUseCaseInput) {


            var userId = messageUseCaseInput.getUserId();

            var user = userService.findByUserId(userId);

            if (user == null) {
                var thread = openAiGateway.createThread();
                userService.save(UserEntity.builder()
                        .userId(thread.getId()).build());
            }

            openAiGateway.createMessage(messageUseCaseInput.getUserId(), messageUseCaseInput.getMessage());

            var run = openAiGateway.createRun(messageUseCaseInput.getUserId());

            return null;









    }
}
