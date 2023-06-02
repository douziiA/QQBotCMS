package com.douzii.botcms.controller;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import com.douzii.botcms.service.BotLoginServiceImpl;
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
    BotLoginServiceImpl botLoginService;
    @Autowired
    BotContainer botContainer;

    /**
     * @path GET/code
     * 获取qq二维码
     * @param qq
     * @return
     */
    @GetMapping(value = "/code",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] QRCode(@RequestParam long qq) throws BotCMSException {
        if (botContainer.hasBot(qq)){
            throw new BotCMSException(HttpStatus.OK,"此账号已经登录");
        }else {
            return botLoginService.getQRCode(qq);
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
            System.out.println(bot.isOnline());
        }
        return new Result(HttpStatus.OK,bots);
    }


}
