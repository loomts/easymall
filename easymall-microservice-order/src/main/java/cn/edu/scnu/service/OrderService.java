package cn.edu.scnu.service;

import cn.edu.scnu.mapper.OrderMapper;
import com.easymall.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    void saveOrder(Order order);

    List<Order> queryMyOrder(String userId);

    void deleteOrder(String orderId);
}
