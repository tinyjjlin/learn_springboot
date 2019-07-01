package com.tiny.springbootmybatisxml.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/7/1
 */
@Data
public class NewsDomain {
    private String title;
    private JSONObject picture;
}
