package com.douzii.miraibot.solver;

import com.douzii.miraibot.container.BotAuthorizationContainer;
import com.douzii.miraibot.container.BotContainer;
import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.QRCodeLoginListener;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            @Override
            public void onFetchQRCode(@NotNull Bot bot, @NotNull byte[] bytes) {
                botAuthorizationContainer.addAuthorizationCode(bot.getId(),bytes);
            }

            @Override
            public void onStateChanged(@NotNull Bot bot, @NotNull QRCodeLoginListener.State state) {
                switch (state){
                    case CONFIRMED -> {
                        botContainer.addBot(bot);
                        botAuthorizationContainer.deleteCode(bot.getId());
                    }
                }
            }
        };
    }
}
