package cn.edu.scnu.service;

import cn.edu.scnu.mapper.OrderMapper;
import com.easymall.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void saveOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderTime(new Date());
        order.setOrderPaystate(0);
        orderMapper.saveOrder(order);
    }

    @Override
    public List<Order> queryMyOrder(String userId) {
        return orderMapper.queryOrder(userId);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderMapper.deleteOrder(orderId);
    }
}
