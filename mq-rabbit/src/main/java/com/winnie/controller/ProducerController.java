package com.winnie.controller;

import com.winnie.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    AmqpTemplate rabbitTemplate;

    @GetMapping("/produce")
    public String produce(@RequestParam(name = "msg") String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, msg);
        return "msg send: " + msg;
    }
}
