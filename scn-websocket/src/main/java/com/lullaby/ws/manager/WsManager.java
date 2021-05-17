package com.lullaby.ws.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Slf4j
@Component
public class WsManager {

    public void sendMessage(String message, Session toSession) {
        try {
            log.info("sendMessage msg: " + message);
            if (toSession == null) {
                log.error("toSession is null");
                return;
            }
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("sendMessage error, msg: " + e.getMessage());
        }
    }
    public void closeSession(Session toSession) {
        try {
            log.info("closeSession");
            if (toSession == null) {
                log.error("toSession is null");
                return;
            }
            toSession.close();
        } catch (Exception e) {
            log.error("closeSession error, msg: " + e.getMessage());
        }
    }
}
