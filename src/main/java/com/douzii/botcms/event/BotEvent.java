package com.douzii.botcms.event;

import com.douzii.botcms.MiraibotApplication;
import com.douzii.botcms.socket.BotServer;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import kotlin.sequences.DistinctSequence;
import kotlin.sequences.Sequence;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.data.PluginDataExtensions;
import net.mamoe.mirai.console.extensions.PluginLoaderProvider;
import net.mamoe.mirai.console.internal.plugin.PluginManagerImpl;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginLoader;
import net.mamoe.mirai.console.plugin.loader.AbstractFilePluginLoader;
import net.mamoe.mirai.console.plugin.loader.FilePluginLoader;
import net.mamoe.mirai.console.plugin.loader.PluginLoader;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

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
