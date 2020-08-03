package com.winnie.controller;

import com.winnie.feign.ProduceServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign客户端
 */
@RestController
public class ConsumerController {
    @Autowired
    ProduceServiceFeign produceServiceFeign;

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(500);
        return produceServiceFeign.hello();
    }

    @GetMapping("/who")
    public String who() throws InterruptedException {
        Thread.sleep(2000);
        return "i think 2 seconds...ok, i am consumer, what about you ?";
    }

}
