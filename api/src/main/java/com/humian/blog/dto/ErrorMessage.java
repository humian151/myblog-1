package com.humian.blog.dto;

/**
 * Created by DELL on 2018/9/14.
 */
public class ErrorMessage {
    private Integer statusCode;
    private String message;
    private String url;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
