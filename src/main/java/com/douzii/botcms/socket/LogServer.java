package com.douzii.botcms.socket;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/logSocket/{qq}")
@Component
public class LogServer {
    public static CopyOnWriteArraySet<LogServer> webSockets = new CopyOnWriteArraySet<>();
    private Session session;
    private long qq;

    public Session getSession() {
        return session;
    }

    public long getQq() {
        return qq;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "qq") long qq) throws IOException {
        this.session = session;
        this.qq = qq;
        webSockets.add(this);


    }
    @OnClose
    public void onClose(){
        webSockets.remove(this);
    }
}
