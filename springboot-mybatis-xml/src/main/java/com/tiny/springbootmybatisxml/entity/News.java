package com.tiny.springbootmybatisxml.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author tiny lin
 * @date 2018/12/5
 */
@Data
public class News {
    private Integer id;
    private String title;
    private String context;
    private String source;
    private Date publishTime;
    private String category;
    private Integer newsNo;
    private String pictrue;
}
