package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 切面，在适当的时机切入相应的程序
public class GlobalExceptionHandler {
    /**
     * @ExceptionHandler相当于controller的@RequestMapping 异常定位调用
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage()); // getMessage()为ServiceException父类中自定义的属性
    }
}
