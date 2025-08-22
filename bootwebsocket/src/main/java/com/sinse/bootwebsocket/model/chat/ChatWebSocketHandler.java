package com.sinse.bootwebsocket.model.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.bootwebsocket.dto.ChatMessage;
import com.sinse.bootwebsocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // 연결된 모든 사용자 세션을 관리
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    // 총 접속자 이름 목록
    private final Set<String> connectedUsers = ConcurrentHashMap.newKeySet();
    // 방 목록 저장소
    private final Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    /*
    * javaEE API @OnOpen
    * - 클라이언트가 연결을 맺으면 자동으로 호출
    * - 사용자 접속 정보 저장, 환영 메시지 전송
    * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.debug("새로운 연결: " + session.getId());
    }

    /*
    * javaEE API @OnMessage
    * - 클라이언트가 메시지를 전송할 때 호출
    * - 메시지 브로드캐스팅(다른 사용자에게 전달)
    * */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("클라이언트 메시지: " + message.getPayload());

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload().toString(), ChatMessage.class);

        switch(chatMessage.getType()) {
            case "CONNECT" -> {
                // 1. 사용자명 관리
                connectedUsers.add(chatMessage.getSender());
                // 2. 전체 접속자 목록을 모든 클라이언트에 broadcast
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
                String uuid = UUID.randomUUID().toString();
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setRoomId(uuid);
                chatRoom.setRoomName(chatMessage.getContent());
                chatRooms.put(uuid, chatRoom);

                broadcast("/rooms", new ArrayList<>(chatRooms.values()));
            }
            case "ROOM_LIST" -> {

            }
            case "ROOM_ENTER" -> {
                ChatRoom room = chatRooms.get(chatMessage.getRoomId());
                if(room != null) room.getUsers().add(chatMessage.getSender());
                broadcast("/rooms", new ArrayList<>(chatRooms.values()));
            }
            case "ROOM_LEAVE" -> {
                ChatRoom room = chatRooms.get(chatMessage.getRoomId());
                if(room != null) room.getUsers().remove(chatMessage.getSender());
                broadcast("/rooms", new ArrayList<>(chatRooms.values()));
            }
        }
    }

    /* 
    * javaEE API @OnError
    * - 통신 오류 발생 시 호출
    * */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket 에러: ", exception);
    }

    /*
    * javaEE API @OnClose
    * - 클라이언트 연결 종료 시 호출
    * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("연결 종료: " + session.getId());
    }

    /*
    * 메시지 부분 처리 지원 여부. 대부분 false.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void broadcast(String destination, Object data) throws Exception{
        String json = objectMapper.writeValueAsString(
               Map.of("destination", destination, "body", data)
        );

        for(WebSocketSession session : sessions) {
            if(session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }
}
