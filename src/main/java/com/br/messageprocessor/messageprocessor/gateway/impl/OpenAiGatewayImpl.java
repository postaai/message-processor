package com.br.messageprocessor.messageprocessor.gateway.impl;

import com.br.messageprocessor.messageprocessor.gateway.OpenAiGateway;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.RunCreateRequest;
import com.theokanning.openai.runs.SubmitToolOutputsRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenAiGatewayImpl implements OpenAiGateway {

   public static final String USER = "user";
   private final OpenAiService openAiClient;


   @Override
   public void createMessage(String threadId,String message) {
      var messageRequest = MessageRequest.builder().role(USER).content(message).build();
      openAiClient.createMessage(threadId,messageRequest);
   }

   @Override
   public Thread createThread() {
      var threadRequest = ThreadRequest.builder().build();
      return openAiClient.createThread(threadRequest);
   }

   @Override
   public Run createRun(String threadId) {
      var runRequest = RunCreateRequest.builder().assistantId("asst_sVmJSAWXTV7Cdkn495cQaMO7").build();
      return openAiClient.createRun(threadId,runRequest);
   }

   @Override
   public Run retrieveRun(String threadId,String runId) {
      return openAiClient.retrieveRun(threadId,runId);
   }

   @Override
   public Run submitToolOutputs(String threadId, String runId, SubmitToolOutputsRequest toolOutputsRequest) {
      return openAiClient.submitToolOutputs(threadId,runId,toolOutputsRequest);
   }

   @Override
   public List<Message> listMessages(String threadId) {
      return openAiClient.listMessages(threadId).data;
   }
}
