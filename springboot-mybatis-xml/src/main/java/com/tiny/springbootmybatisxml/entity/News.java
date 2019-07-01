package com.tiny.springbootmybatisxml.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author tiny lin
 * @date 2018/12/5
 */
@Data
public class News implements Serializable {
    private Integer id;
    private String title;
    private JSONObject picture;
//    private String context;
//    private String source;
//    private Date publishTime;
//    private String category;
//    private Integer newsNo;

}
