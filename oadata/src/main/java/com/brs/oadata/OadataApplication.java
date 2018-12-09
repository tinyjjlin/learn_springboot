package com.brs.oadata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.brs.oadata.*.mapper"})
public class OadataApplication {

    public static void main(String[] args) {
        SpringApplication.run(OadataApplication.class, args);
    }
}
