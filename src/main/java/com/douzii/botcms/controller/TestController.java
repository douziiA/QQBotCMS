package com.douzii.botcms.controller;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import com.douzii.botcms.solver.BotLoginSolver;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public byte[] QRCode(@RequestParam long qq) throws BotCMSException {

        if (!botAuthorizationContainer.getCodeMap().containsKey(qq)){
          throw new BotCMSException(HttpStatus.BAD_REQUEST,"请先开始登录操作");
        }

        return botAuthorizationContainer.getCode(qq);
    }

    /**
     * @path POST/login
     * 登录qq机器人/手表协议
     * @param qq qq号
     * @return
     */
//    @PostMapping("/login")
    public Result login(@RequestParam long qq) throws BotCMSException {
        if (botContainer.hasBot(qq)){
            throw new BotCMSException(HttpStatus.BAD_REQUEST,"已经登录过了");
        }

        Bot bot = BotFactory.INSTANCE.newBot(qq, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setLoginSolver(botLoginSolver);
        });
        bot.login();

        if (bot.isOnline()){
            return new Result(HttpStatus.OK,"登录成功");
        }else{
            return new Result(HttpStatus.UNAUTHORIZED,"验证失败：请重新登录");
        }

    }

    /**
     * @path GET/
     * 获取已登录的机器人
     * @return
     */
    @GetMapping
    public Result getBots(){
        List<Long> bots = new ArrayList<>();
        for (Bot bot : Bot.getInstances()) {
            bots.add(bot.getId());
        }
        return new Result(HttpStatus.OK,bots);
    }


}
