package cn.edu.scnu.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.scnu.entity.User;
import cn.edu.scnu.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	public User queryUser(String userId) {
		return userMapper.queryUser(userId);
	}
	public void updatePoint(String userId, Integer moneyAndPoint) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("money", moneyAndPoint);
		userMapper.updateUserPoint(map);
	}

}
