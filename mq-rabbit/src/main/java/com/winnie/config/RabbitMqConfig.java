package com.winnie.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit工作原理
 * 根据路由规则rounting-key，将主题topic绑定到交换机（exchange）上
 */
@Configuration
public class RabbitMqConfig {
    // 队列
    public final static String QUEUE_NAME = "myqueue";
    // 交换机
    @Value("spring.rabbitmq.template.exchange")
    public static String EXCHANGE_NAME;
    // 路由key
    @Value("spring.rabbitmq.template.routing-key")
    public static String ROUNTING_KEY;
    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
}
