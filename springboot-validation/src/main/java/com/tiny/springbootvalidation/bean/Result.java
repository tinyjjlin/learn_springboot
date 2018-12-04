package com.tiny.springbootvalidation.bean;

import lombok.Data;

/**
 * @author tiny lin
 * @date 2018/12/2
 */
@Data
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result(){}
    public Result(int code,String message){
        this(code,message,null);
    }
    public Result(int code,String message,Object data){
        this.code = code;
        this.message  = message;
        this.data = data;
    }
}
