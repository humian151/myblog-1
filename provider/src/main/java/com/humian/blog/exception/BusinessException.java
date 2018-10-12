package com.humian.blog.exception;

/**
 * Created by DELL on 2018/9/14.
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String msg) {
        super(msg);
    }
}
