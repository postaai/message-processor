package com.br.messageprocessor.messageprocessor.useCase.impl;

import com.br.messageprocessor.messageprocessor.entity.Enum.StatusEnum;
import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.br.messageprocessor.messageprocessor.service.UserService;
import com.br.messageprocessor.messageprocessor.useCase.ValidateMessageUseCase;
import com.br.messageprocessor.messageprocessor.useCase.dto.ValidationMessageUseCaseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValidateMessageUseCaseImpl implements ValidateMessageUseCase {

    private final UserService userService;

    @Override
    public Boolean execute(ValidationMessageUseCaseInput input) {

        var user = userService.findByUserId(input.getUserId());

        if (Objects.equals(user.getStatus(), StatusEnum.COMPLETED.getDescription())) {
            return true;
        }

        return false;
    }
}
