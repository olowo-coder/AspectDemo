package com.example.aspecttest.exception;

import java.util.Date;

public class ApplicationErrors {

    private String message;
    private Date date;
    private String code;

    private String path;

    public ApplicationErrors(String message, String code, String path) {
        this.message = message;
        this.code = code;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
