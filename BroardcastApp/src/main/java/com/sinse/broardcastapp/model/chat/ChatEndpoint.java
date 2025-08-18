package com.sinse.broardcastapp.model.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/* javaEE API (spring X) */
@Slf4j
@ServerEndpoint("/ws/multi")
@Component
public class ChatEndpoint {
    private static Set<Session> sessions = new HashSet<>();

    // 0. 접속자 명단
    private static Set<String> userIdList = new HashSet<String>();
    private static ObjectMapper objectMapper = new ObjectMapper();

    // 1. 접속 감지
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws JsonProcessingException {
        log.debug("================ onOpen called ================");
        log.debug("sessionId = {}", session.getId());
        sessions.add(session);

        userIdList.add(session.getId());
        // 접속과 동시에 클라이언트에게 서버의 접속자 명단을 전송하자
        String json = objectMapper.writeValueAsString(userIdList);
        session.getAsyncRemote().sendText(json);
        log.debug("=============== // onOpen called ===============");
    }

    // 2. 메시지 감지
    @OnMessage
    public void onMessage(Session session, String message) {
        log.debug("================ onMessage called ===============");
        log.debug("sessionId = {}", session.getId());
        log.debug("클라이언트 메시지 : " + message);
        for (Session s : sessions) {
            s.getAsyncRemote().sendText("서버 메시지 : " + message);
        }
        log.debug("=============== // onMessage called ==============");
    }
}