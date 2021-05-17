package com.lullaby.ws.manager;

import com.lullaby.ws.common.Constants;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

public class WsManager {

    private static ConcurrentHashMap<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();

    public static void add(String key, WebSocketSession socketSession) {
        sessionPool.put(key, socketSession);
    }

    public static void remove(String key) {
        sessionPool.remove(key);
    }

    public static WebSocketSession get(String key) {
        return sessionPool.get(key);
    }

    // 获取session对应的用户sid
    public static String getWebSocketSid(WebSocketSession session) {
        if (session != null) {
            URI uri = session.getUri();
            if (uri != null) {
                return uri.getPath().replace(Constants.WS_PREFIX, "").replace("/", "").trim();
            }
        }
        return null;
    }
}
