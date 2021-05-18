package com.lullaby.controller;

import com.lullaby.ws.WsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@RestController
public class WsController {

    @RequestMapping("/ping/{sid}")
    public String ping(@PathVariable String sid) throws IOException {
        List<WebSocketSession> list = WsManager.getSessionsBySid(sid);
        for (WebSocketSession session : list) {
            session.sendMessage(new PingMessage(ByteBuffer.wrap(("ping " + sid).getBytes())));
        }
        return "success";
    }

    @RequestMapping("/pong/{sid}")
    public String pong(@PathVariable String sid) throws IOException {
        List<WebSocketSession> list = WsManager.getSessionsBySid(sid);
        for (WebSocketSession session : list) {
            session.sendMessage(new PongMessage(ByteBuffer.wrap(("pong " + sid).getBytes())));
        }
        return "success";
    }
}
