package cn.edu.scnu.user.service;


import com.easymall.pojo.User;

public interface UserService {
    int checkUserName(String userName);
    void userSave(User user);
}