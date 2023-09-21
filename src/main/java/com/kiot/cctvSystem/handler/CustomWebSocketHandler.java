package com.kiot.cctvSystem.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 웹소켓 연결이 확립된 후 실행되는 코드
        log.info("WebSocket connection established. Session ID: " + session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 웹소켓 통신 중 오류 발생 시 처리
        log.info("websocket error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("WebSocket connection closed. Session ID: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // 메시지를 보내는 메소드
    public static void sendMessageToAll(String url) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}