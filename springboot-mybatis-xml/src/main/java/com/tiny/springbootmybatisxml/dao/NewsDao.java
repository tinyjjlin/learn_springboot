package com.tiny.springbootmybatisxml.dao;

import com.tiny.springbootmybatisxml.entity.News;

import java.util.List;

/**
 * @author tiny lin
 * @date 2018/12/5
 */
public interface NewsDao {
    /**
     * 获取新闻列表(all)
     * @return
     */
    public List<News> list();
}
