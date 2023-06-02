package com.douzii.miraibot.container;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 二维码存储容器
 */
@Component
public class BotAuthorizationContainer {
    private Map<Long,byte[]> codeMap = new LinkedHashMap<>();

    public BotAuthorizationContainer(){}

    public Map<Long, byte[]> getCodeMap() {
        return codeMap;
    }

    public void setCodeMap(Map<Long, byte[]> codeMap) {
        this.codeMap = codeMap;
    }

    /**
     * 添加qq登录的二维码
     * @param qq
     * @param code
     */
    public void addAuthorizationCode(long qq,byte[] code){
        this.codeMap.put(qq,code);
    }

    /**
     * 根据qq获取二维码
     * @param qq
     * @return
     */

    public byte[] getCode(long qq){
        return this.codeMap.get(qq);
    }

    /**
     * 删除二维码
     * @param qq
     */
    public void deleteCode(long qq){
        this.codeMap.remove(qq);

    }
}
