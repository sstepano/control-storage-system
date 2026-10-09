package org.code_studio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    //@Scheduled(fixedRate = 2, timeUnit = TimeUnit.SECONDS)
    public void sendWebsocketMessage(String message) {
        this.template.convertAndSend("/topic/greetings", message);
    }

    public void sendStatusMessage(String message) {
        this.template.convertAndSend("/topic/statusmessages", message);
    }

}