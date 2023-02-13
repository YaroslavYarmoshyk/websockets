package com.websockets.controller;

import com.websockets.model.Greeting;
import com.websockets.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class WebSocketController {

    @MessageMapping("/news")
    @SendTo("/topic/news")
    public Greeting broadCastNews(@Payload Message message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }

    @MessageMapping("/greetings")
    @SendToUser("/queue/greetings")
    public String reply(@Payload String message,
                        Principal user) {
        return  "Hello " + message;
    }
}
