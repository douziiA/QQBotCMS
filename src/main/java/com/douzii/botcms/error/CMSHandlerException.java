package com.douzii.botcms.error;

import com.douzii.botcms.entity.Result;
import com.douzii.botcms.exception.BotCMSException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@RestControllerAdvice
public class CMSHandlerException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handler(RuntimeException runtimeException){
        if (runtimeException instanceof BotCMSException){
            return handler2((BotCMSException) runtimeException);
        }
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        ResponseEntity<Result> entity = builder.body(new Result(HttpStatus.INTERNAL_SERVER_ERROR, "服务器出现了问题请联系管理员进行维修"));
        return entity;
    }

    public ResponseEntity handler2(BotCMSException botCMSException){
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        ResponseEntity<Result> entity = builder.body(new Result(botCMSException.getStatus(), botCMSException.getMessage()));
        return entity;
    }

}
