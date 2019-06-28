package com.zyt.itoken.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.zyt.itoken")
@MapperScan(basePackages = {"com.zyt.itoken.service.admin.mapper", "com.zyt.itoken.common.mapper"})
@EnableEurekaClient
public class ServiceAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run (ServiceAdminApplication.class, args);
    }
}
