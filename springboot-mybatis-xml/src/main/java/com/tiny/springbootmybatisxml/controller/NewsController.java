package com.tiny.springbootmybatisxml.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiny.springbootmybatisxml.dao.NewsDao;
import com.tiny.springbootmybatisxml.entity.News;
import com.tiny.springbootmybatisxml.entity.NewsDomain;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tiny lin
 * @date 2019/7/1
 */
@Slf4j
@RestController
@Api
public class NewsController {

    @Autowired
    private NewsDao newsDao;

    @PostMapping("/api/news")
    public String create(@RequestBody News news){
        log.info("params:=-=================>"+ JSONObject.toJSONString(news));
        newsDao.insert(news);
        return "add news";
    }

    @GetMapping("/api/news")
    public Object query(@RequestParam("id")Integer id){
        NewsDomain newsDomain = newsDao.select(id);
        String p = JSONObject.toJSONString(newsDomain.getPicture());
        log.info("=============>"+p);
        return JSONObject.toJSONString(newsDomain);
    }

}
