package com.learning.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.learning.project.mapper")//扫描
public class LearnCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnCommunityApplication.class, args);
    }

}
