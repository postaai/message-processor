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
import java.util.concurrent.TimeUnit;

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
            System.out.println("------------------------> mensagem chegou no useCase: " + messageUseCaseInput.getMessage());
//            if (!validateMessageUseCase.execute(ValidationMessageUseCaseInput.builder().userId(userId).build())) {
//                System.out.println("------------------------> Thread is busy");
//                throw new ThreadBusyException();
//            }
            return processMessage(user, messageUseCaseInput);
        } else {
            var newUser = createThread(userId);
            return processMessage(newUser, messageUseCaseInput);
        }

    }

    private MessageUseCaseOutput processMessage(UserEntity user, MessageUseCaseInput messageUseCaseInput) {
        System.out.println("------------------------> Processando mensagem chegou no useCase: " + messageUseCaseInput.getMessage());

        try {
            System.out.println("------------------------> Creating message " + messageUseCaseInput.getMessage());
            openAiGateway.createMessage(user.getThreadId(), messageUseCaseInput.getMessage());
        } catch (OpenAiHttpException e) {
            System.out.println("------------------------> Error creating message " + messageUseCaseInput.getMessage());
            throw new ThreadBusyException();
        }

        System.out.println("------------------------> Error creating message " + messageUseCaseInput.getMessage());


        System.out.println("------------------------> Creating run " + messageUseCaseInput.getMessage());
        var run = openAiGateway.createRun(user.getThreadId());
        System.out.println("------------------------> Run created " + messageUseCaseInput.getMessage());

        if (Objects.equals(checkStatus(run, user.getUserId()), StatusEnum.COMPLETED)) {
            System.out.println("------------------------> Getting message " + messageUseCaseInput.getMessage());
            var lastAssistantMessage = listMessage(user.getThreadId(), 0).getContent().stream().findFirst().get().getText().getValue();
            ;
            return MessageUseCaseOutput.builder().message(lastAssistantMessage).status(StatusEnum.COMPLETED.getDescription()).build();

        } else {
            throw new MessageProcessError();
        }
    }

    private Message listMessage(String threadId, Integer attempt) {

        if (attempt > 5) {
            throw new MessageProcessError();
        }

        try {
            List<Message> messages = openAiGateway.listMessages(threadId);
            var message = messages.stream().findFirst().get();
            if (Objects.equals(message.getRole(), "user")) {
                TimeUnit.SECONDS.sleep(1);
                return listMessage(threadId, attempt + 1);
            }
            return messages.stream().findFirst().get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MessageProcessError();
        }


    }

    private StatusEnum checkStatus(Run run, String userId) {

        try {
            TimeUnit.MILLISECONDS.sleep(1000);

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
