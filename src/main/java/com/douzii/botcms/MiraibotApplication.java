package com.douzii.botcms;

import com.douzii.botcms.event.BotEvent;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.internal.MiraiConsoleImplementationBridge;
import net.mamoe.mirai.console.internal.plugin.BuiltInJvmPluginLoaderImpl;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginLoader;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.internal.deps.io.ktor.util.debug.plugins.PluginTraceElement;
import net.mamoe.mirai.utils.MiraiLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {

        //mirai-console启动
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(new MiraiConsoleImplementationTerminal());


        //springboot启动
        SpringApplication.run(MiraibotApplication.class, args);
    }

}
