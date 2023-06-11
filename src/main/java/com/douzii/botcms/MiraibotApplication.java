package com.douzii.botcms;

import com.douzii.botcms.event.BotEvent;
import kotlin.Lazy;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.GlobalScope;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.MiraiConsoleFrontEndDescription;
import net.mamoe.mirai.console.MiraiConsoleImplementation;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.data.PluginDataStorage;
import net.mamoe.mirai.console.internal.MiraiConsoleBuildConstants;
import net.mamoe.mirai.console.internal.MiraiConsoleBuildDependencies;
import net.mamoe.mirai.console.internal.MiraiConsoleImplementationBridge;
import net.mamoe.mirai.console.internal.plugin.PluginManagerImpl;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginLoader;
import net.mamoe.mirai.console.plugin.loader.PluginLoader;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;
import net.mamoe.mirai.console.util.ConsoleInput;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoginSolver;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraibotApplication.class, args);
        MiraiConsoleTerminalLoader.main(args);
        PluginManager pluginManager = MiraiConsole.INSTANCE.getPluginManager();
    }

}
