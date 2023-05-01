package cn.edu.scnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.entity.User;
import cn.edu.scnu.service.UserService;

@RestController
public class UserController {
@Autowired
private UserService userService;
	@RequestMapping("user/query/point")
	public User queryUser(String userId){
		return userService.queryUser(userId);
		 
	}
	@RequestMapping("user/updatePoint")
	public Integer updatePoint(@RequestParam(value="orderMoney")Integer moneyAndPoint,String userId){
		userService.updatePoint(userId,moneyAndPoint);
		return 1;
	}


}
