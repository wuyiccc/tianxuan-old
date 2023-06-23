package com.wuyiccc.hire.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:01
 */
@SpringBootApplication
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.hire.user.mapper")
public class HireUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HireUserApplication.class, args);
    }
}
