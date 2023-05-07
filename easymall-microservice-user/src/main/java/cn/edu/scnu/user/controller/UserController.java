package cn.edu.scnu.user.controller;

import cn.edu.scnu.user.service.UserService;
import com.easymall.pojo.User;
import com.easymall.utils.CookieUtils;
import com.easymall.utils.MD5Util;
import com.easymall.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        try {
            userService.userSave(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }

    @RequestMapping("/login")
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        String ticket = userService.doLogin(user);
        if (!ticket.isEmpty()) {
            CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
            return SysResult.ok();
        }
        return SysResult.build(201, "登录失败", null);
    }

    @RequestMapping("/query/{ticket}")
    public SysResult checkLoginUser(@PathVariable String ticket) {
        String userJson = userService.queryUser(ticket);
        if (!StringUtils.isEmpty(userJson)) {
            return SysResult.build(200, "ok", userJson);
        }
        return SysResult.build(201, "", null);
    }
}
