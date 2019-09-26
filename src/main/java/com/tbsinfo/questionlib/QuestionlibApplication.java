package com.tbsinfo.questionlib;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tbsinfo.questionlib.dao")
public class QuestionlibApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionlibApplication.class, args);
    }

}
