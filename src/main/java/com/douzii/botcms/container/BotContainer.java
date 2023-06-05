package com.douzii.botcms.container;

import net.mamoe.mirai.Bot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 机器人容器
 */
@Component
public class BotContainer {
    private List<Bot> botList = new ArrayList();


    public BotContainer(){

    }

    public List<Bot> getBotList() {
        return botList;
    }

    public void setBotList(List<Bot> botList) {
        this.botList = botList;
    }

    /**
     * 添加机器人到容器里面
     * @param bot
     */
    public void addBot(Bot bot){
        boolean flag = this.botList.stream().filter(botPojo -> botPojo.getId() == bot.getId()).findAny().isEmpty();
        if (!flag){
            return;
        }

        this.botList.add(bot);
    }

    /**
     * 获取qq机器人
     * @param qq
     * @return
     */
    public Bot getBot(long qq){
        return Bot.getInstances().stream().filter(bot -> bot.getId() == qq).findFirst().orElse(null);
    }

    /**
     * 判断qq机器人是否登录
     * @param qq
     * @return
     */
    public static boolean hasBot(long qq){
        return !Bot.getInstances().stream().filter(bot -> bot.getId() == qq && bot.isOnline()).findFirst().isEmpty();
    }
}
