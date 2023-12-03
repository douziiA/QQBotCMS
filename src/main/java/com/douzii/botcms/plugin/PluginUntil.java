package com.douzii.botcms.plugin;

import lombok.Data;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.console.plugin.PluginManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class PluginUntil {
    public static Map<String,Plugin> plugins = new HashMap<>();

    public static void disable(String id){
        if (PluginUntil.plugins.size() == 0){
            PluginManager.INSTANCE.getPlugins().forEach(plugin -> PluginUntil.plugins.put(PluginManager.INSTANCE.getPluginDescription(plugin).getId(),plugin));
        }
        PluginManager.INSTANCE.disablePlugin(plugins.get(id));
    }

}
