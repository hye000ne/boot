package com.sinse.bootwebsocket.dto;

import lombok.Data;
/**
 * 서버와 클라이언트의 상호작용을 위한 메시지 전달 객체
 * 1) type
 * - CONNECT : 접속
 * - DISCONNECT : 접속 해제
 * - MESSAGE : 채팅
 * - ROOM_CREATE : 방 생성
 * - ROOM_LIST : 방 목록
 * - ROOM_ENTER : 방 입장
 * - ROOM_LEAVE : 방 퇴장
 * 2) sender : 전송자
 * 3) content : 메시지 내용
 * 4) roomId : 방 ID
 */
@Data
public class ChatMessage {
    private String type;
    private String sender;
    private String content;
    private String roomId;
}
