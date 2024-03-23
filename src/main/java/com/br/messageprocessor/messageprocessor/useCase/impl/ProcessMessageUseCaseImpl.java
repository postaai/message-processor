package com.br.messageprocessor.messageprocessor.useCase.impl;

import com.br.messageprocessor.messageprocessor.entity.Enum.StatusEnum;
import com.br.messageprocessor.messageprocessor.exception.MessageProcessError;
import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.br.messageprocessor.messageprocessor.service.UserService;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.runs.Run;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProcessMessageUseCaseImpl implements ProcessMessageUseCase {
    @Autowired
    private OpenAiGateway openAiGateway;
    @Autowired
    private UserService userService;

    @Override
    public MessageUseCaseOutput process(MessageUseCaseInput messageUseCaseInput) {

        var userId = messageUseCaseInput.getUserId();
        var user = userService.findByUserId(userId);


        if (user == null) {

            openAiGateway.createMessage(user.getThreadId(), messageUseCaseInput.getMessage());

            var run = openAiGateway.createRun(messageUseCaseInput.getUserId());

            if (Objects.equals(checkStatus(run), StatusEnum.COMPLETED)) {
                String messageText;
                List<Message> messages = openAiGateway.listMessages(user.getThreadId());

                var messageData = messages.stream().findFirst().get();


                if (Objects.equals(messageData.getRole(), "assistant")) {
                    messageText = messageData.getContent().stream().findFirst().get().getText().getValue();
                    return MessageUseCaseOutput.builder()
                            .message(messageText)
                            .status(StatusEnum.COMPLETED.getDescription()).build();
                }


            } else {
                throw new MessageProcessError();
            }


        }

        throw new MessageProcessError();

    }


    private StatusEnum checkStatus(Run run) {

        try {
            Thread.sleep(1000);

            var retrieveRun = openAiGateway.retrieveRun(run.getThreadId(), run.getId());


            if (Objects.equals(retrieveRun.getStatus(), StatusEnum.REQUIRES_ACTION.getDescription())) {
                processToolCalls(retrieveRun);
            } else if (Objects.equals(retrieveRun.getStatus(), StatusEnum.COMPLETED.getDescription())) {
                return StatusEnum.COMPLETED;
            } else if (Objects.equals(retrieveRun.getStatus(), StatusEnum.IN_PROGRESS.getDescription())) {
                return checkStatus(retrieveRun);
            }
            return checkStatus(retrieveRun);
        } catch (Exception e) {
            return StatusEnum.FAILED;
        }
    }

    private void processToolCalls(Run run) {
    }
}
