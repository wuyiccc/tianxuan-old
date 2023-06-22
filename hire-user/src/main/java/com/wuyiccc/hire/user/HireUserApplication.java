package com.wuyiccc.hire.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:01
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wuyiccc.hire.user.mapper")
public class HireUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HireUserApplication.class, args);
    }
}
