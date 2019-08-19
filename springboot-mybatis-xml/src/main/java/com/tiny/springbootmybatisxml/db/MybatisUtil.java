package com.tiny.springbootmybatisxml.db;

import com.tiny.springbootmybatisxml.dao.NewsDao;
import com.tiny.springbootmybatisxml.entity.News;
import com.tiny.springbootmybatisxml.entity.NewsDomain;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import javax.sql.DataSource;

/**
 * @author tiny lin
 * @date 2019/7/24
 */
public class MybatisUtil {
    DefaultObjectFactory
    SqlSessionFactoryBean
    public void getSqlSessionFactory(){
        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(News.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

         SqlSession sqlSession = sqlSessionFactory.openSession();
         NewsDao newsDao = sqlSession.getMapper(NewsDao.class);
         newsDao.select(1);
         sqlSession.
    }
}
