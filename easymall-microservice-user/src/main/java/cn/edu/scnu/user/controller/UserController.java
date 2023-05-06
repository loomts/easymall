package cn.edu.scnu.user.controller;

import cn.edu.scnu.user.service.UserService;
import com.easymall.pojo.User;
import com.easymall.utils.MD5Util;
import com.easymall.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/user/manage")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/checkUserName")
    public SysResult checkUserName(String userName) {
        int exist = userService.checkUserName(userName);
        if (exist == 0) return SysResult.ok();
        else return SysResult.build(201, "已存在", null);
    }

    @RequestMapping("/save")
    public SysResult userSave(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        try {
            userService.userSave(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }
}
