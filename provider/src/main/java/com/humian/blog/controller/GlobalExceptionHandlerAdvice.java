package com.humian.blog.controller;

import com.humian.blog.dto.ErrorMessage;
import com.humian.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常类
 * Created by DELL on 2018/9/14.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ErrorMessage bussinessException(HttpServletRequest request,Exception e){
        System.out.println("进入异常信息...");
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorMessage.setMessage(e.getLocalizedMessage());
        errorMessage.setUrl(request.getRequestURI().toString());
        return errorMessage;
    }
}
