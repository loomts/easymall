package cn.edu.scnu.controller;

import cn.edu.scnu.config.RabbitmqConfig;
import cn.edu.scnu.service.SecService;
import cn.edu.scnu.service.SucService;
import com.easymall.pojo.Seckill;
import com.easymall.pojo.Success;
import com.easymall.vo.SysResult;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill/manage")
public class SecController {
    @Autowired
    private SecService secService;
    @Autowired
    private SucService sucService;

    @RequestMapping("/list")
    public List<Seckill> queryAll() {
        return secService.queryAll();
    }

    @RequestMapping("/detail")
    public Seckill queryOne(String seckillId) {
        return secService.queryOne(seckillId);
    }

    @Autowired
    private RabbitTemplate client;

    @RequestMapping("/{seckillId}")
    public SysResult startSeckill(@PathVariable long seckillId) {
        String userId = "180888" + RandomUtils.nextInt(10000, 99999);
        String msg = userId + "/" + seckillId;
        client.convertAndSend(RabbitmqConfig.exName, RabbitmqConfig.routingKey, msg);
        return SysResult.ok();
    }


    @RequestMapping("/{seckillId}/userPhone")
    public List<Success> querySuccess(@PathVariable long seckillId) {
        return sucService.queryAllSuccess(seckillId);
    }

}
