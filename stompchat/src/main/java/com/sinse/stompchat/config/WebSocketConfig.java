package com.sinse.stompchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 웹소켓 실시간 통신(채팅, 알림 등) 환경을 위한 설정 클래스
 * - STOMP 프로토콜 + 메시지브로커 활용 (확장성과 관리가 유리)
 */
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 관련 설정
     *  - enableSimpleBroker("/topic", "/queue"):
     *      "topic"으로 시작하는 목적지로 메시지를 전달하는 간단한 내장 브로커를 활성화
     *      topic - 전체 구독용, queue - 1:1 메시지용
     *  - setApplicationDestinationPrefixes("/app"):
     *      : 클라이언트가 서버에 메시지를 보낼 때 사용할 prefix 지정
     * @param registry
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * STOMP 웹소켓 엔드포인트 등록
     * - addEndpoint("/ws"):
     *      클라이언트가 연결할 웹소켓 주소 (채팅방 입구)
     * - withSockJS():
     *      웹소켓 미지원 브라우저도 연결 가능하도록 지원
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
}
