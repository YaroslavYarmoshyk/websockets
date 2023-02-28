package com.websockets.controller;

import com.websockets.model.Greeting;
import com.websockets.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/news")
    @SendTo("/topic/news")
    public Greeting broadCastNews(@Payload Message message) {
        return new Greeting("news");
    }

    @MessageMapping("/greetings")
    @SendTo("/queue/greetings")
    public Greeting reply(@Payload Message message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }
}
