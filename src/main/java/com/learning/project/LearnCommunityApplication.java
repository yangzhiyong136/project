package com.learning.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描
@MapperScan("com.learning.project.mapper")
public class LearnCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnCommunityApplication.class, args);
    }

}
