package com.lullaby.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * 一次心跳指的是
 *      (client/server)ping --> (server/client)OnPing --> (server/client)pong
 *
 * 对于spring boot websocket来说
 * client发送ping消息 --> OnPing --> 自动发送pong消息到client端(spring boot ws收到OnPing消息，自动发送pong消息(携带了ping中的完整消息内容)到client端)
 * client发送pong消息 --> OnPong --> 不自动发送消息(原因在于一次心跳指的是ping-pong，收到pong已经表示此次心跳正常)
 *
 * 目前client使用的websocket client也是如此
 * server发送ping消息 --> OnPing --> 自动发送pong消息到client端(client的ws框架收到OnPing消息，自动发送pong消息(携带了ping中的完整消息内容)到client端)
 * server发送pong消息 --> OnPong --> 不自动发送消息(原因在于一次心跳指的是ping-pong，收到pong已经表示此次心跳正常)
 *
 */
@Slf4j
@Component
public class WsHandler extends AbstractWebSocketHandler {

    // 等同于@OnOpen
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished, sid: " + WsManager.getSidFromSession(session));
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
        log.info("handlePongMessage, message: " + new String(message.getPayload().array()));
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
        log.info("afterConnectionClosed, sid: " + WsManager.getSidFromSession(session));
        WsManager.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("supportsPartialMessages");
        return false;
    }
}
