package com.douzii.botcms.controller;

import com.douzii.botcms.entity.Result;
import com.douzii.botcms.plugin.PluginUntil;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.plugin.description.PluginDescription;
import net.mamoe.mirai.console.plugin.loader.PluginLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("plugins")
@CrossOrigin
@RestController
public class BotPluginController {
    @GetMapping
    public Result list(){
        return new Result(HttpStatus.OK, PluginManager.INSTANCE.getPlugins().stream().map(plugin -> PluginManager.INSTANCE.getPluginDescription(plugin)).collect(Collectors.toList()));
    }
//    @PutMapping
//    public Result disable(@RequestBody String id){
//        PluginUntil.disable(id);
//        return new Result(HttpStatus.OK, "卸载成功");
//    }
}
