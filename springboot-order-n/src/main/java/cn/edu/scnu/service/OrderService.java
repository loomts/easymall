package cn.edu.scnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.edu.scnu.entity.Order;
import cn.edu.scnu.mapper.OrderMapper;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private RestTemplate client;
	
	public void orderPay(String orderId) {
		Order order = orderMapper.queryOrder(orderId);
		System.out.println(orderId + "订单已被" + order.getUserId() + "付款，金额是：" + order.getOrderMoney());
		// 支付查询出来的订单金额已经完成
		
		try {
			Integer success = client.getForObject("http://userservice/user/updatePoint?orderMoney="
					+ order.getOrderMoney() + "&userId=" + order.getUserId(), Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
