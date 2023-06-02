package com.douzii.miraibot.container;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public void addAuthorizationCode(long qq,byte[] code){
        this.codeMap.put(qq,code);
    }
    public byte[] getCode(long qq){
        return this.codeMap.get(qq);
    }
    public void deleteCode(long qq){
        this.codeMap.remove(qq);

    }
}
