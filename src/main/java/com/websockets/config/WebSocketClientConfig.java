package com.websockets.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import static com.websockets.Constants.REGISTRY_ENDPOINT;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketClientConfig {
    private final SessionHandler sessionHandler;

    @Bean
    public WebSocketClient webSocketClient() {
        final WebSocketClient webSocketClient = new StandardWebSocketClient();
        final WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        webSocketStompClient.connectAsync("ws://localhost:8081" + REGISTRY_ENDPOINT, sessionHandler);
        return webSocketClient;
    }
}
