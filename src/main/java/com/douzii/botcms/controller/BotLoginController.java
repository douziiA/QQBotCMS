package com.douzii.botcms.controller;

import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import com.douzii.botcms.service.BotLoginServiceImpl;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("bot")

public class BotLoginController {
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
        List<Map> bots = new ArrayList<>();

        for (Bot bot : Bot.getInstances()) {
            Map map = new HashMap();
            map.put("qq",bot.getId());
            map.put("online",bot.isOnline());
            if (bot.isOnline()){
                map.put("avatar",bot.getAvatarUrl());
                map.put("group",bot.getGroups().size());
                map.put("friend",bot.getFriends().size());
            }

            bots.add(map);
        }

        return new Result(HttpStatus.OK,bots);
    }
    @DeleteMapping
    public Result exitBot(@RequestParam long qq){
        if (!botContainer.hasBot(qq)){
            throw new BotCMSException(HttpStatus.UNAUTHORIZED,"此账号未登录");
        }

        botContainer.getBot(qq).close();
        return new Result(HttpStatus.OK,"退出成功");
    }


}
