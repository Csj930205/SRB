package com.srb.srb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 URL => /ws-stomp
        registry
                .addEndpoint("/ws-stomp") // 연결될 엔드포인트
                .withSockJS(); // SocketJS 연결 설정
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지를 구독하는 요청 URL => 메세지를 받아올때
        registry.enableSimpleBroker("/sub");

        // 메세지를 발행하는 요청 URL => 메세지를 보낼때
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
