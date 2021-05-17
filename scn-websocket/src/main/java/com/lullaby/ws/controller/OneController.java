package com.lullaby.ws.controller;

import com.lullaby.ws.link.WebSocketServer;
import com.lullaby.ws.manager.WsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OneController {

    @Autowired
    private WsManager wsManager;

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("/send/{sid}/{msg}")
    public String send(@PathVariable String sid, @PathVariable String msg) {

        wsManager.sendMessage(msg, WebSocketServer.getSession(sid));
        return "one";
    }

    @RequestMapping("/close/{sid}")
    public String close(@PathVariable String sid) {
        log.info("close");

        wsManager.closeSession(WebSocketServer.getSession(sid));
        return "one";
    }

}
