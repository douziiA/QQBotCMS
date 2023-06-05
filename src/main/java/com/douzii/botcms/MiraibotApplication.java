package com.douzii.botcms;

import com.douzii.botcms.event.BotEvent;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.internal.deps.io.ktor.util.debug.plugins.PluginTraceElement;
import net.mamoe.mirai.utils.MiraiLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraibotApplication.class, args);

    }

}
