package cn.edu.scnu.mapper;

import com.easymall.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void saveOrder(Order order);

    List<Order> queryOrder(String userId);

    void deleteOrder(String orderId);
}
