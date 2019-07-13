package com.zyt.itoken.service.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;


// 秒杀服务，因为并发较高需要另开一个数据库
@EnableAsync
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.zyt.itoken")
@MapperScan(basePackages = {"com.zyt.itoken.common.mapper"})
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run (SeckillApplication.class, args);
    }
}