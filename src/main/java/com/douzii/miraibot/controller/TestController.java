package com.douzii.miraibot.controller;

import com.douzii.miraibot.container.BotAuthorizationContainer;
import com.douzii.miraibot.container.BotContainer;
import com.douzii.miraibot.solver.BotLoginSolver;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("bot")

public class TestController {
    @Autowired
    BotLoginSolver botLoginSolver;
    @Autowired
    BotContainer botContainer;
    @Autowired
    BotAuthorizationContainer botAuthorizationContainer;

    /**
     * @path GET/code
     * 获取qq二维码
     * @param qq
     * @return
     */
    @GetMapping(value = "/code",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] QRCode(@RequestParam long qq){
        return botAuthorizationContainer.getCode(qq);
    }

    /**
     * @path POST/login
     * 登录qq机器人/手表协议
     * @param qq qq号
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam long qq){
        if (botContainer.hasBot(qq)){
            return "该QQ已经登录了";
        }

        Bot bot = BotFactory.INSTANCE.newBot(qq, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setLoginSolver(botLoginSolver);
        });
        bot.login();
        if (bot.isOnline()){
            return "登录成功";
        }else {
            return "登录失败";
        }
    }

    /**
     * @path GET/
     * 获取已登录的机器人
     * @return
     */
    @GetMapping
    public List<Long> getBots(){
        List<Long> bots = new ArrayList<>();
        for (Bot bot : botContainer.getBotList()) {
            bots.add(bot.getId());
        }
        return bots;
    }

}
