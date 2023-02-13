package com.websockets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.websockets.Constants.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint(REGISTRY_ENDPOINT)
                    .setAllowedOrigins(REGISTRY_DOMAIN).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(MESSAGE_BROKER_TOPIC, MESSAGE_BROKER_QUEUE);
        registry.setApplicationDestinationPrefixes(DESTINATION_PREFIX);
    }
}
