package com.br.messageprocessor.messageprocessor.useCase.impl;

import com.br.messageprocessor.messageprocessor.entity.Enum.StatusEnum;
import com.br.messageprocessor.messageprocessor.entity.UserEntity;
import com.br.messageprocessor.messageprocessor.exception.MessageProcessError;
import com.br.messageprocessor.messageprocessor.exception.ThreadBusyException;
import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.br.messageprocessor.messageprocessor.service.UserService;
import com.br.messageprocessor.messageprocessor.useCase.ProcessMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.ValidateMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;
import com.br.messageprocessor.messageprocessor.useCase.dto.ValidationMessageUseCaseInput;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.runs.Run;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProcessMessageUseCaseImpl implements ProcessMessageUseCase {

    private final OpenAiGateway openAiGateway;
    private final UserService userService;
    private final ValidateMessageUseCase validateMessageUseCase;

    @Override
    public MessageUseCaseOutput process(MessageUseCaseInput messageUseCaseInput) {

        var userId = messageUseCaseInput.getUserId();
        var user = userService.findByUserId(userId);

        if (user != null) {
            if (!validateMessageUseCase.execute(ValidationMessageUseCaseInput.builder().userId(userId).build())) {
                throw new ThreadBusyException();
            }
            return processMessage(user, messageUseCaseInput);
        } else {
            var newUser = createThread(userId);
            return processMessage(newUser, messageUseCaseInput);
        }

    }


    private MessageUseCaseOutput processMessage(UserEntity user, MessageUseCaseInput messageUseCaseInput) {


        System.out.println("------------------------>Creating message");
        openAiGateway.createMessage(user.getThreadId(), messageUseCaseInput.getMessage());
        System.out.println("------------------------>Creating run");
        var run = openAiGateway.createRun(user.getThreadId());
        System.out.println("------------------------>Run created");


        if (Objects.equals(checkStatus(run, user.getUserId()), StatusEnum.COMPLETED)) {
            String messageText;
            List<Message> messages = openAiGateway.listMessages(user.getThreadId());

            var messageData = messages.stream().findFirst().get();


            if (Objects.equals(messageData.getRole(), "assistant")) {
                messageText = messageData.getContent().stream().findFirst().get().getText().getValue();
                return MessageUseCaseOutput.builder().message(messageText).status(StatusEnum.COMPLETED.getDescription()).build();
            }


        } else {
            throw new MessageProcessError();
        }
        return null;
    }


    private StatusEnum checkStatus(Run run, String userId) {

        try {
            Thread.sleep(1000);

            var retrieveRun = openAiGateway.retrieveRun(run.getThreadId(), run.getId());


            if (Objects.equals(retrieveRun.getStatus(), StatusEnum.REQUIRES_ACTION.getDescription())) {
                processToolCalls(retrieveRun);
            } else if (Objects.equals(retrieveRun.getStatus(), StatusEnum.COMPLETED.getDescription())) {
                updateUserStatus(userId, StatusEnum.COMPLETED);
                return StatusEnum.COMPLETED;
            } else if (Objects.equals(retrieveRun.getStatus(), StatusEnum.IN_PROGRESS.getDescription())) {
                updateUserStatus(userId, StatusEnum.IN_PROGRESS);
                return checkStatus(retrieveRun, userId);
            }
            return checkStatus(retrieveRun, userId);
        } catch (Exception e) {
            return StatusEnum.FAILED;
        }
    }

    private void updateUserStatus(String userId, StatusEnum status) {
        var user = userService.findByUserId(userId);
        user.setStatus(status.getDescription());
        userService.save(user);
    }

    private UserEntity createThread(String userId) {
        var thread = openAiGateway.createThread();

        return userService.save(UserEntity.builder()
                .threadId(thread.getId())
                .userId(userId)
                .build());
    }

    private void processToolCalls(Run run) {
    }
}
