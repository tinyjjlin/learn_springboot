package com.tiny.ui.model;

import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
@Data
public class RestResult {
    private Integer code;
    private String message;
    private Object data;

    public RestResult(Integer code,String message){
        this(code,message,null);
    }
    public RestResult(Integer code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
