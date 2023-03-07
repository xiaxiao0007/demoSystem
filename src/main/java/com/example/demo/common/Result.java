package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 统一的包装类，将后端的返回的结果封装起来
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(Code.CODE_200.getCode(),"成功",null);
    }

    public static Result success(Object data){
        return  new Result(Code.CODE_200.getCode(), "成功",data);
    }

    public static Result error(String code, String msg){
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(Code.CODE_500.getCode(), "系统错误",null);
    }
}
