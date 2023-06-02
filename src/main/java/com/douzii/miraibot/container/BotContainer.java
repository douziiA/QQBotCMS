package com.douzii.miraibot.container;

import net.mamoe.mirai.Bot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
    public Bot getBot(long qq){
        return this.botList.stream().filter(bot -> bot.getId() == qq).findFirst().orElse(null);
    }
    public boolean hasBot(long qq){
        return !this.botList.stream().filter(bot -> bot.getId() == qq).findAny().isEmpty();
    }
}
