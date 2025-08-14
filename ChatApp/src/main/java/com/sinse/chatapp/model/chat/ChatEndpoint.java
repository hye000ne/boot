package com.sinse.chatapp.model.chat;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ServerEndpoint("/ws/echo")
@Component
@Slf4j
public class ChatEndpoint {
    // 접속 감지
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        log.debug("onOpen called. sessionId = "+ session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.debug("onMessage called. sessionId = "+ session.getId() + ", message = " + message);

        // 클라이언트에게 메시지 전송
        session.getBasicRemote().sendText("server: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        log.debug("onClose called. sessionId = "+ session.getId()+", closeReason = "+closeReason);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("onError called. sessionId = "+ session.getId() + ", error = " + error);
    }
}


