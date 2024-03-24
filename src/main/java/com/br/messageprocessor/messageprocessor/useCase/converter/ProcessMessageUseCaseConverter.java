package com.br.messageprocessor.messageprocessor.useCase.converter;

import com.br.messageprocessor.messageprocessor.controller.dto.MessageRequest;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageResponse;
import com.br.messageprocessor.messageprocessor.entity.UserEntity;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseInput;
import com.br.messageprocessor.messageprocessor.useCase.dto.MessageUseCaseOutput;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProcessMessageUseCaseConverter {

    MessageResponse toMessageResponse(MessageUseCaseOutput messageUseCaseOutput);

    MessageUseCaseInput toMessageUseCaseInput(MessageRequest messageRequest);


}
