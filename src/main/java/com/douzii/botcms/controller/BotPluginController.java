package com.douzii.botcms.controller;

import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.plugin.description.PluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginLoader;
import net.mamoe.mirai.console.plugin.loader.PluginLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plugin")
public class BotPluginController {
    public static Plugin getPlugin(String id){
        List<JvmPlugin> plugins = JvmPluginLoader.BuiltIn.listPlugins();
        for (JvmPlugin plugin : plugins) {
            if (plugin.getDescription().getId().equals(id)){
                return plugin;
            }
        }

        return null;
    }
    @GetMapping
    public Result getPlugins(){

        List<Map> lists = new ArrayList<>();

        List<JvmPlugin> plugins = JvmPluginLoader.BuiltIn.listPlugins();

        for (Plugin plugin : plugins) {
            Map map = new HashMap<>();
            PluginDescription des = PluginManager.INSTANCE.getPluginDescription(plugin);
            map.put("id",des.getId());
            map.put("name",des.getName());
            map.put("author",des.getAuthor());
            map.put("description",des.getInfo());
            map.put("version",des.getVersion());
            map.put("isEnable",plugin.isEnabled());
            lists.add(map);
        }
        return new Result(HttpStatus.OK,lists);
    }
    @GetMapping("/reload")
    public Result reloadPlugin(){
        List<JvmPlugin> jvmPlugins = JvmPluginLoader.BuiltIn.listPlugins();
        for (JvmPlugin plugin : jvmPlugins) {
            System.out.println(plugin.getDescription().getName());
            try {
                JvmPluginLoader.BuiltIn.load(plugin);
            }catch (IllegalStateException e){
                System.out.println(plugin.getDescription().getName() + "已经加载过了");
            }catch (Exception e){
                System.out.println(e);
            }

        }
        return new Result(HttpStatus.OK,"插件加载成功");
    }
    @GetMapping("/enable")
    public Result pluginEnable(@RequestParam String id){

        Plugin plugin = getPlugin(id);
        if (plugin == null){
            throw new BotCMSException(HttpStatus.NOT_FOUND,"此插件找不到");
        }

        try {
            JvmPluginLoader.BuiltIn.enable((JvmPlugin) plugin);
        }catch (Exception e){
            System.out.println(e);
            System.out.println(e.getMessage());
        }


        return new Result(HttpStatus.OK,"插件启动成功");
    }

    @GetMapping("/disable")
    public Result pluginDisable(@RequestParam String id){
        Plugin plugin = getPlugin(id);

        if (plugin == null){
            throw new BotCMSException(HttpStatus.NOT_FOUND,"此插件找不到");
        }

        JvmPluginLoader.BuiltIn.disable((JvmPlugin) plugin);
        return new Result(HttpStatus.OK,"插件关闭成功");
    }


}
