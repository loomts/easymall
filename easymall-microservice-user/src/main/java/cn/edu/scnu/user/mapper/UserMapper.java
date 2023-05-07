package cn.edu.scnu.user.mapper;

import com.easymall.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int checkUserName(String userName);
    void userSave(User user);
    User doLogin(User user);
}