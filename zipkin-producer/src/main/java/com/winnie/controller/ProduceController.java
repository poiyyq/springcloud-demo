package com.winnie.controller;

import com.winnie.feign.ConsumerServiceFeign;
import com.winnie.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProduceController {
    @Autowired
    ConsumerServiceFeign consumerServiceFeign;
    @Autowired
    private ProduceService produceService;

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(1000);
        String who = consumerServiceFeign.who();
        return who + ". " +produceService.hello();
    }

}
