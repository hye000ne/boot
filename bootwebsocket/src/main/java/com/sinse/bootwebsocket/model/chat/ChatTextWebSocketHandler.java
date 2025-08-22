package com.sinse.bootwebsocket.model.chat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.bootwebsocket.dto.ChatMessage;
import com.sinse.bootwebsocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
Spring에서 웹소켓 server endpoint를 다루는 객체는 WebSocketHandler만 있는게 아님
[TextWebSocketHandler]
- 다루고자 하는 데이터가 Text일 경우 효율적
- 인터페이스가 아니므로, 사용되지도 않는 메서드를 재정의할 필요 없다. 즉 필요한 것만 골라서 재정의
 */
@Slf4j
@Component
public class ChatTextWebSocketHandler extends TextWebSocketHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    // 현재 서버에 연결되어 있는 모든 클라이언트 세션 집합 (클라이언트 전송용 아님)
    private Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private Set<String> connectedUsers = ConcurrentHashMap.newKeySet();
    private Map<String, ChatRoom> roomStorage = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    // 모든 클라이언트가 동시에 알아야할 정보를 전송할 브로드케스트 메서드 정의
    private void broadcast(String destination, Object data) throws Exception {
        String json = objectMapper.writeValueAsString(Map.of("destination", destination, "body", data));

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트는 java가 이해할 수 엇는 sjon 문자열 형태로 메시지를 전송하므로,
        // 서버측에서는 해석이 피룡하다.
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        switch (chatMessage.getType()) {
            case "CONNECT" -> {
                connectedUsers.add(chatMessage.getSender());
                broadcast("/users", connectedUsers);
            }
            case "DISCONNECT" -> {
                connectedUsers.remove(chatMessage.getSender());
                broadcast("/users", connectedUsers);
            }
            case "MESSAGE" -> {
                broadcast("/messages", chatMessage);
            }
            case "ROOM_CREATE" -> {
                ChatRoom chatRoom = new ChatRoom();

                String uuid = UUID.randomUUID().toString();
                chatRoom.setRoomId(uuid);
                chatRoom.setRoomName(chatMessage.getContent());
                roomStorage.put(uuid, chatRoom);

                broadcast("/rooms", chatRoom);
            }
            case "ROOM_LIST" -> {
                broadcast("/rooms", roomStorage.values());
            }
            case "ROOM_ENTER" -> {

            }
            case "ROOM_LEAVE" -> {

            }
        }

        // 요청 분기

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }
}
