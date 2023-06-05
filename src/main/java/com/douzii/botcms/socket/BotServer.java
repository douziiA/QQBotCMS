package com.douzii.botcms.socket;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.event.BotEvent;
import com.douzii.botcms.log.BotLogger;
import com.douzii.botcms.solver.BotLoginSolver;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.MiraiLogger;
import net.mamoe.mirai.utils.SimpleLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;


@Slf4j
@ServerEndpoint("/loginSocket/{qq}")
@Component
public class BotServer {
    public static BotLoginSolver botLoginSolver;
    public static BotAuthorizationContainer botAuthorizationContainer;



    @Autowired
    public void setBotAuthorizationContainer(BotAuthorizationContainer botAuthorizationContainer) {
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
            session.getAsyncRemote().sendText("重复登录");
            log.info(qq+"该账号已登录");
            return;
        }

        this.bot = BotFactory.INSTANCE.newBot(qq, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setLoginSolver(botLoginSolver);
            configuration.setBotLoggerSupplier(bot1 -> new BotLogger("Bot", (logPriority, s, throwable) -> {

                List<LogServer> list = LogServer.webSockets.stream().filter(logServer -> logServer.getQq() == bot1.getId()).toList();
                if (list.size() == 0){
                    return null;
                }
                Session logSession = list.get(0).getSession();

                StringBuilder builder = new StringBuilder();
                builder
                        .append(new SimpleDateFormat("YYYY-MM-dd").format(new Date()))
                        .append(" " + logPriority.getNameAligned() + "/" + bot1.getId())
                        .append(" " + s);
                logSession.getAsyncRemote().sendText(builder.toString());

                return null;
            }));

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
