package com.douzii.botcms.event;

import com.douzii.botcms.socket.BotServer;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class BotEvent extends SimpleListenerHost {
    @EventHandler
    public void onMessage(@NotNull BotOnlineEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        Bot bot = event.getBot();
        Session session = BotServer.webSockets.stream().filter(socket -> socket.getQq() == bot.getId()).findFirst().get().getSession();
        session.getAsyncRemote().sendText("登录成功");
        session.close(new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE,"登录成功"));
//        event.getSubject().sendMessage("received");
        // 无返回值, 表示一直监听事件.
    }
}
