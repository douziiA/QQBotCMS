package com.douzii.botcms.config;

import com.douzii.botcms.error.CMSHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Configuration
public class BotConfig extends WebMvcConfigurationSupport {
    @Autowired
    CMSHandlerException cmsHandlerException;

    @Value("${socket.port}")
    private int port;
    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(cmsHandlerException);
    }
    @Bean
    public ServerSocket socket() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        return socket;
    }
}
