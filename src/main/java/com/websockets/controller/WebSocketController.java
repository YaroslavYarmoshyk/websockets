package com.websockets.controller;

import com.websockets.model.Greeting;
import com.websockets.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Greeting broadCastNews(Message message) {
        return new Greeting("Hello1, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }

    @MessageMapping("/greetings")
    @SendTo("/queue/greetings")
    public Greeting greeting(Message message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }
}
