package com.winnie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerApp {
    public static void main(String[] args) {
        SpringApplication.run(DockerApp.class);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello docker";
    }

}
