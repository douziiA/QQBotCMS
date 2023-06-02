package com.douzii.botcms.entity;

import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Result {
    private int status;
    private Object data;

    public Result(HttpStatus status, Object data, String message, String date) {
        this.status = status.value();
        this.data = data;
        this.message = message;
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Result(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());

    }
    public Result(HttpStatus status, Object data) {
        this.status = status.value();
        this.data = data;
        this.date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());

    }

    public Result(HttpStatus status, Object data, String message) {
        this.status = status.value();
        this.data = data;
        this.message = message;
        this.date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
    }

    private String message;
    private String date;
}
