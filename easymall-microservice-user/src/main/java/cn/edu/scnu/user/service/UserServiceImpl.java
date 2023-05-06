package cn.edu.scnu.user.service;

import cn.edu.scnu.user.mapper.UserMapper;
import com.easymall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public int checkUserName(String userName) {
        return userMapper.checkUserName(userName);
    }
    @Override
    public void userSave(User user){
        userMapper.userSave(user);
    }
}
