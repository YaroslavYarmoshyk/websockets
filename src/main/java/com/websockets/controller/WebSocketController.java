package com.websockets.controller;

import com.websockets.model.Greeting;
import com.websockets.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/news")
    @SendTo("/topic/news")
    public Message broadCastNews(Message message) {
        return message;
    }

    @MessageMapping("/greetings")
    @SendTo("/queue/greetings")
    public Greeting greeting(Greeting greeting) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(greeting.name()) + "!");
    }
}
