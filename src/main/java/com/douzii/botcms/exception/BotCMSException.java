package com.douzii.botcms.exception;

import org.springframework.http.HttpStatus;

public class BotCMSException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BotCMSException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
