package cn.edu.scnu.controller;

import cn.edu.scnu.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("send")
    public String sendMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.exName,
                RabbitmqConfig.routingKey, msg);
        return "success";
    }
}
