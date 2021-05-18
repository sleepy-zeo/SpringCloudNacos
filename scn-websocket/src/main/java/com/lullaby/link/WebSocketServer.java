package com.lullaby.link;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket是线程安全的，每有一个用户连接就会创建一个新的实例
 */
@Slf4j
@ServerEndpoint("/ws/{sid}")
@Component
public class WebSocketServer {

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private static final Map<String, Session> sessionPool = new ConcurrentHashMap<>();

    public static Session getSession(String sid) {
        if (sid == null) {
            sid = "";
        }
        return sessionPool.get(sid);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String sid) {
        onlineCount.incrementAndGet();
        sessionPool.put(sid, session);

        log.info("onOpen sid: " + sid + ", current online: " + onlineCount);
    }

    @OnClose
    public void onClose(Session session, @PathParam(value = "sid") String sid) {
        onlineCount.decrementAndGet();
        sessionPool.remove(sid);

        log.info("onClose sid: " + sid + ", current online: " + onlineCount);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam(value = "sid") String sid) {
        sessionPool.remove(sid);

        log.error("onError sid: " + sid + ", msg: " + error);
        try {
            session.close();
        } catch (IOException e) {
            log.error("session close error, msg: " + e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam(value = "sid") String sid) {
        log.info("onMessage sid: " + sid + ", msg: " + message);
    }

    @OnMessage
    public void onPong(PongMessage message, Session session, @PathParam(value = "sid") String sid) {
        log.info("onPong sid: " + sid + ", msg: " + message.getApplicationData().toString());
    }
}
