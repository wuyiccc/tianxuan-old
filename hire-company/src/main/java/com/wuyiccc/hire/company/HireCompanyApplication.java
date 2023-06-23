package com.wuyiccc.hire.company;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuyiccc
 * @date 2023/6/19 23:02
 */
@SpringBootApplication
// 开启注册中心的服务注册和发现功能
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wuyiccc.hire.company.mapper")
public class HireCompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(HireCompanyApplication.class, args);
    }
}
