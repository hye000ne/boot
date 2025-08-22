package com.sinse.bootwebsocket.model.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
* Spring WebSocket Endpoint(URL) 및 클라이언트 요청 처리 객체(핸들러) 등록
* - @Configuration : 해당 클래스를 설정 파일임을 지정.
* - @RequiredArgsConstructor : final 필드를 자동으로 생성자에 포함
* */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    // private final ChatWebSocketHandler webSocketHandler;
    private final ChatTextWebSocketHandler webSocketHandler;

    /**
     * - /ws 경로로 들어오는 WebSocket 요청을 ChatWebSocketHandler가 처리하도록 등록
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins("*");
    }
}
