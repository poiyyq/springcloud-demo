package com.winnie.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.winnie.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class ProducerController2 {

    @GetMapping("/produce2")
    public String produce(@RequestParam(name = "msg") String msg) throws IOException, TimeoutException {

        // 创建链接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("centos7");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitMqConfig.QUEUE_NAME, true, false, false, null);
        channel.basicPublish("", RabbitMqConfig.QUEUE_NAME, null, msg.getBytes());
        channel.close();
        connection.close();
        return "msg send: " + msg;
    }
}
