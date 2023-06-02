package com.douzii.botcms;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.internal.deps.io.ktor.util.debug.plugins.PluginTraceElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {
        GlobalEventChannel.INSTANCE.parentScope((CoroutineScope) GlobalEventChannel.INSTANCE.getDefaultCoroutineContext());
        SpringApplication.run(MiraibotApplication.class, args);

    }

}
