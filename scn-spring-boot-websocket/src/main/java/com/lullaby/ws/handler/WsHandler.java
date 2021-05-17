package com.lullaby.ws.handler;

import com.lullaby.ws.manager.WsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Component
public class WsHandler extends AbstractWebSocketHandler {

    // 等同于@OnOpen
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished, sid: " + WsManager.getWebSocketSid(session));
        WsManager.add(session.getId(), session);
    }

    // 等同于@OnMessage
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        log.info("handleMessage");
        if (message instanceof TextMessage) {
            handleTextMessage(session, (TextMessage) message);
        } else if (message instanceof BinaryMessage) {
            handleBinaryMessage(session, (BinaryMessage) message);
        } else {
            if (!(message instanceof PongMessage)) {
                throw new IllegalStateException("Unexpected WebSocket message type: " + message);
            }

            this.handlePongMessage(session, (PongMessage) message);
        }
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        log.info("handleTextMessage");
    }

    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws Exception {
        log.info("handleBinaryMessage");

    }

    @Override
    protected void handlePongMessage(@NonNull WebSocketSession session, @NonNull PongMessage message) throws Exception {
        log.info("handlePongMessage");

    }

    // 等同于@OnError
    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        log.info("handleTransportError, msg: " + exception.getMessage());
        session.close();
    }

    // 等同于@OnClose
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("afterConnectionClosed, sid: " + WsManager.getWebSocketSid(session));
        WsManager.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("supportsPartialMessages");
        return false;
    }
}
