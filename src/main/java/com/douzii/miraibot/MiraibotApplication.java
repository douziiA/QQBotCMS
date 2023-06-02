package com.douzii.miraibot;

import com.douzii.miraibot.solver.BotLoginSolver;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.StandardCharImageLoginSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiraibotApplication {

    public static void main(String[] args) {

        SpringApplication.run(MiraibotApplication.class, args);

    }

}
