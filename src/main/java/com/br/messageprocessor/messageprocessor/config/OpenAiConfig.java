package com.br.messageprocessor.messageprocessor.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    @Value("${openai.api.key}")
    private String openApiKey;
    @Bean
    public OpenAiService openAiClient(){
        return new OpenAiService(openApiKey);
    }
}
