package com.mpf.forwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@ServletComponentScan
@MapperScan("com.mpf.forwork.mapper")
@SpringBootApplication(scanBasePackages = "com.mpf.forwork")
public class ForworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForworkApplication.class, args);
    }

}