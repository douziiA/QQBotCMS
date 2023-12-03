package com.douzii.botcms.config;

import com.douzii.botcms.error.CMSHandlerException;
import com.douzii.botcms.event.BotEvent;
import com.douzii.botcms.plugin.PluginUntil;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.console.plugin.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotConfig extends WebMvcConfigurationSupport {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


}
