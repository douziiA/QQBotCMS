package com.douzii.botcms;

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraibotApplication.class, args);
        MiraiConsoleTerminalLoader.main(args);
    }

}
