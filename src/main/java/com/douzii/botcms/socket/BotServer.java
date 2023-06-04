package com.douzii.botcms.socket;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.event.BotEvent;
import com.douzii.botcms.solver.BotLoginSolver;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@Slf4j
@ServerEndpoint("/loginSocket/{qq}")
@Component
public class BotServer {
    public static BotLoginSolver botLoginSolver;
    public static BotAuthorizationContainer botAuthorizationContainer;


    @Autowired
    public  void setBotAuthorizationContainer(BotAuthorizationContainer botAuthorizationContainer) {
        BotServer.botAuthorizationContainer = botAuthorizationContainer;
    }

    @Autowired
    public void setBotLoginSolver(BotLoginSolver botLoginSolver) {
        BotServer.botLoginSolver = botLoginSolver;
    }

    public static CopyOnWriteArraySet<BotServer> webSockets = new CopyOnWriteArraySet<>();
    private Session session;

    private long qq;
    private Thread thread;
    private Bot bot;
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "qq")long qq) throws IOException {
        this.session = session;
        this.qq = qq;
        webSockets.add(this);

        System.out.println(BotContainer.hasBot(qq));
        if (BotContainer.hasBot(qq)){
            session.close();
            log.info(qq+"该账号已登录");
            return;
        }

        this.bot = BotFactory.INSTANCE.newBot(qq, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setLoginSolver(botLoginSolver);
        });
        this.thread = new Thread(bot::login);
        thread.start();

    }


    public long getQq() {
        return qq;
    }

    public Session getSession() {
        return session;
    }

    @OnClose
    public void onClose(CloseReason closeReason){
        webSockets.remove(this);
        botAuthorizationContainer.deleteCode(qq);
        thread.interrupt();
        if (closeReason.getCloseCode() != CloseReason.CloseCodes.NO_STATUS_CODE){
            bot.close();
        }
    }
}
