package com.douzii.botcms.socket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
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

    public static void main(String[] args) throws IOException {

    }
}
