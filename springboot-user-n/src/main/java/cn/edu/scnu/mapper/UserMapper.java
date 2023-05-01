package cn.edu.scnu.mapper;

import java.util.Map;

import cn.edu.scnu.entity.User;

public interface UserMapper {
	User queryUser(String userId);
	void updateUserPoint(Map<String, Object> map);
}
