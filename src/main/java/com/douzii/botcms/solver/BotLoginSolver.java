package com.douzii.botcms.solver;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.event.BotEvent;
import com.douzii.botcms.socket.BotServer;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.QRCodeLoginListener;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义登录解决器
 */
@Component
public class BotLoginSolver extends LoginSolver {
    @Autowired
    BotAuthorizationContainer botAuthorizationContainer;

    @Autowired
    BotContainer botContainer;
    @Autowired
    BotEvent botEvent;

    @Nullable
    @Override
    public Object onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        return null;
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        return null;
    }



    @NotNull
    @Override
    public QRCodeLoginListener createQRCodeLoginListener(@NotNull Bot bot) {
        return new QRCodeLoginListener() {
            /**
             * 二维码更新调用的方法
             */
            @Override
            public void onFetchQRCode(@NotNull Bot bot, @NotNull byte[] bytes) {

                botAuthorizationContainer.deleteCode(bot.getId());
                botAuthorizationContainer.addAuthorizationCode(bot.getId(),bytes);
                Session session = BotServer.webSockets.stream().filter(socket -> socket.getQq() == bot.getId()).findFirst().get().getSession();
                session.getAsyncRemote().sendText("开始验证");
            }

            /**
             * 登录后调用的方法
             */
            @Override
            public void onStateChanged(@NotNull Bot bot, @NotNull QRCodeLoginListener.State state) {
                Session session = BotServer.webSockets.stream().filter(socket -> socket.getQq() == bot.getId()).findFirst().get().getSession();
                switch (state){
                    case CONFIRMED -> {
                        botAuthorizationContainer.deleteCode(bot.getId());
                        bot.getEventChannel().registerListenerHost(botEvent);
                        session.getAsyncRemote().sendText("扫码成功");

                    }
                    case CANCELLED -> {
                        session.getAsyncRemote().sendText("取消扫码");
                    }
                    case TIMEOUT -> {
                        session.getAsyncRemote().sendText("扫码过期");
                    }
                    case WAITING_FOR_CONFIRM -> {
                        System.out.println("确认中");
                        session.getAsyncRemote().sendText("确认中");
                    }

                }
            }
        };
    }
}
