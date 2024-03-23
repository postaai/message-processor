package com.br.messageprocessor.messageprocessor.controller.impl;

import com.br.messageprocessor.messageprocessor.controller.MessageController;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageRequest;
import com.br.messageprocessor.messageprocessor.controller.dto.MessageResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageControllerImpl implements MessageController {


    @Override
    public MessageResponse processMessage(MessageRequest messageRequest) {
        return null;
    }
}
