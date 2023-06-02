package com.douzii.botcms.solver;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.QRCodeLoginListener;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义登录解决器
 */
@Component
public class BotLoginSolver extends LoginSolver {
    @Autowired
    BotAuthorizationContainer botAuthorizationContainer;

    @Autowired
    BotContainer botContainer;
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
            }
            /**
             * 登录后调用的方法
             */
            @Override
            public void onStateChanged(@NotNull Bot bot, @NotNull QRCodeLoginListener.State state) {
                switch (state){
                    case CONFIRMED -> {
                        botContainer.addBot(bot);
                        botAuthorizationContainer.deleteCode(bot.getId());
                    }
                    case CANCELLED -> {
                        botAuthorizationContainer.deleteCode(bot.getId());
                    }
                }
            }
        };
    }
}
