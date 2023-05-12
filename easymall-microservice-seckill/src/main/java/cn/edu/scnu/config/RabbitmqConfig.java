package cn.edu.scnu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String exName = "seckillEx";
    public static final String qName = "seckillQueue";
    public static final String routingKey = "seckill";

    @Bean
    public DirectExchange exInit() {
        return new DirectExchange(exName, false, false);
    }

    @Bean
    public Queue queueInit() {
        return new Queue(qName, false, false, false);
    }

    @Bean
    public Binding bindInit() {
        return BindingBuilder.bind(queueInit()).to(exInit()).with(routingKey);
    }
}
