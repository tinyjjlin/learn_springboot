package com.tiny.springbootmybatisxml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tiny.springbootmybatisxml.dao")
public class SpringbootMybatisXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisXmlApplication.class, args);
    }
}
