package cn.edu.scnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	@RequestMapping("/order/pay")
	public Integer orderPay(String orderId){
		try{
			orderService.orderPay(orderId);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
