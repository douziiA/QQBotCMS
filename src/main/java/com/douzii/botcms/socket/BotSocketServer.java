package com.douzii.botcms.socket;

import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class BotSocketServer {
    @Autowired
    ServerSocket serverSocket;
    private static final ThreadPoolExecutor SocketThreadPool = new ThreadPoolExecutor(15, 15,
            10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @PostConstruct
    public void start() throws IOException {
        new Thread(()->{
            try {
                while (true){
                    Socket socket = serverSocket.accept();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        },"start").start();
    }
}
