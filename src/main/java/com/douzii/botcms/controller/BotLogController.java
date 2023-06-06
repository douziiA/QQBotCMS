package com.douzii.botcms.controller;

import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import net.mamoe.mirai.Bot;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("log")
@CrossOrigin
@RestController
public class BotLogController {
    @GetMapping("/{qq}")
    public Result getLog(@PathVariable long qq) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("log/" + qq + "/" + new SimpleDateFormat("YYYY-MM-dd").format(new Date()) + ".log"));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append("<div class='logDetail'>"+line+"</div>");
            }
            return new Result(HttpStatus.OK,builder.toString(),"");
        }catch (IOException e){
            throw new BotCMSException(HttpStatus.UNAUTHORIZED,"此账号未登录");
        }

    }
}
