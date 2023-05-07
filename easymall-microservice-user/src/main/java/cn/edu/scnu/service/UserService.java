package cn.edu.scnu.service;


import com.easymall.pojo.User;

public interface UserService {
    int checkUserName(String userName);
    void userSave(User user);
    String doLogin(User user);
    String queryUser(String ticket);
}
