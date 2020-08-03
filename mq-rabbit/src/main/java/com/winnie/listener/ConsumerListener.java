package com.winnie.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {
    @RabbitListener(queues = "myqueue")
    public void consume(byte[] bytes){
        String msg = new String(bytes);
        System.out.println("consume msg :" +msg);
    }
}
