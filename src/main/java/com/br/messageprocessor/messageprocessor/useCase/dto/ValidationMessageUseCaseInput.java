package com.br.messageprocessor.messageprocessor.useCase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationMessageUseCaseInput {
   private String userId;
   private String threadId;
   private String runId;
}
