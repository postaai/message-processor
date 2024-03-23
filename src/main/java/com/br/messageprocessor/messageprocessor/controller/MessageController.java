package com.br.messageprocessor.messageprocessor.controller;

import com.br.messageprocessor.messageprocessor.controller.dto.MessageRequest;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageResponse;
import org.springframework.web.bind.annotation.PostMapping;

public interface MessageController {


    @PostMapping("/create")
     MessageResponse processMessage(MessageRequest messageRequest);
}
