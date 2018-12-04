package com.tiny.springbootmybatisxml.mapper;

import com.tiny.springbootmybatisxml.dao.NewsDao;
import com.tiny.springbootmybatisxml.entity.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author tiny lin
 * @date 2018/12/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsMapperTest {

    @Autowired
    private NewsDao newsDao;

    @Test
    public void listNews(){
        List<News> newsList = newsDao.list();
        newsList.forEach(System.out::print);
    }
}
