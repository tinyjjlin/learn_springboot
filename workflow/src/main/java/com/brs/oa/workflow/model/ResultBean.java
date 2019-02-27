package com.brs.oa.workflow.model;

import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@Data
public class ResultBean {
    private Integer code;
    private String message;
    private Object data;

    public ResultBean(Integer code,String message){
        this(code,message,null);
    }
    public ResultBean(Integer code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
