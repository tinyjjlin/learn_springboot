package com.tiny.springbootmybatisxml.dao;

import com.tiny.springbootmybatisxml.entity.News;
import com.tiny.springbootmybatisxml.entity.NewsDomain;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

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

    @Insert("insert into news(title,picture)values(#{news.title},#{news.picture,typeHandler=com.tiny.springbootmybatisxml.dao.MySqlJsonHandler})")
    void insert(@Param("news")News news);


    @Select("select title,picture from news where id =#{id}")
    @Results(
            @Result(column = "picture", property = "picture", typeHandler = MySqlJsonHandler.class)
    )

    NewsDomain select(@Param("id")Integer id);


}
