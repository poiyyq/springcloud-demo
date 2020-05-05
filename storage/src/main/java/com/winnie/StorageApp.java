package com.winnie;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 库存服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
public class StorageApp {
    public static void main(String[] args) {
        SpringApplication.run(StorageApp.class,args);
    }
}
