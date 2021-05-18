package com.lullaby.ws;

import com.lullaby.common.Constants;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
    public static String getSidFromSession(WebSocketSession session) {
        if (session != null) {
            URI uri = session.getUri();
            if (uri != null) {
                return uri.getPath().replace(Constants.WS_PREFIX, "").replace("/", "").trim();
            }
        }
        return null;
    }

    // 获取sid对应的session集合
    public static List<WebSocketSession> getSessionsBySid(String sid) {
        if (sid == null || sid.trim().equals("")) {
            return null;
        }
        List<WebSocketSession> list = new ArrayList<>();
        for (WebSocketSession webSocketSession : sessionPool.values()) {
            if (sid.equals(getSidFromSession(webSocketSession))) {
                list.add(webSocketSession);
            }
        }
        return list;
    }
}
