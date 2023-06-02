package com.douzii.botcms.service;

import com.douzii.botcms.container.BotAuthorizationContainer;
import com.douzii.botcms.container.BotContainer;
import com.douzii.botcms.exception.BotCMSException;
import com.douzii.botcms.solver.BotLoginSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BotLoginServiceImpl {
    @Autowired
    BotLoginSolver botLoginSolver;
    @Autowired
    BotContainer botContainer;
    @Autowired
    BotAuthorizationContainer botAuthorizationContainer;
    public byte[] getQRCode(long qq){
        if (!botAuthorizationContainer.getCodeMap().containsKey(qq)){
            throw new BotCMSException(HttpStatus.BAD_REQUEST,"请先开始登录操作");
        }

        return botAuthorizationContainer.getCode(qq);
    }
}
