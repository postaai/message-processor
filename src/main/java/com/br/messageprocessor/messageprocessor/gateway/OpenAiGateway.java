package com.br.messageprocessor.messageprocessor.gateway;

import com.theokanning.openai.messages.Message;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.SubmitToolOutputsRequest;
import com.theokanning.openai.threads.Thread;

import java.util.List;

public interface OpenAiGateway {


    Thread createThread();

    void createMessage(String threadId,String message);

    Run createRun(String threadId);

    Run retrieveRun(String threadId, String runId);

    Run submitToolOutputs(String threadId, String runId, SubmitToolOutputsRequest toolOutputsRequest);

    List<Message> listMessages(String threadId);
}
