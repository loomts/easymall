package cn.edu.scnu.user.service;

import cn.edu.scnu.user.mapper.UserMapper;
import com.alibaba.fastjson2.JSON;
import com.easymall.pojo.User;
import com.easymall.utils.MD5Util;
import com.easymall.utils.PrefixKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate redis;

    @Override
    public int checkUserName(String userName) {
        return userMapper.checkUserName(userName);
    }

    @Override
    public void userSave(User user) {
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        userMapper.userSave(user);
    }

    @Override
    public String doLogin(User user) {
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        User userInfo = userMapper.doLogin(user);
        if (userInfo == null) {
            return "";
        }
        String ticket = PrefixKey.USER_LOGIN_TICKET + user.getUserId() + System.currentTimeMillis();
        String userJson = JSON.toJSONString(userInfo);
        String loginCheckKey = PrefixKey.USER_LOGINED_CHECK_PREFIX + userInfo.getUserId();
        String oldTicket = redis.opsForValue().get(loginCheckKey);
        if (!oldTicket.isEmpty()) {
            redis.delete(oldTicket);
        }
        redis.opsForValue().set(loginCheckKey, ticket);
        log.info("LOGIN " + userInfo.getUserName() + "::" + ticket);
        redis.opsForValue().set(ticket, userJson, 15L, TimeUnit.MINUTES);
        return ticket;
    }

    @Override
    public String queryUser(String ticket) {
        String userJson;
        try {
            Long lifeTime = redis.getExpire(ticket, TimeUnit.SECONDS);
            userJson = redis.opsForValue().get(ticket);
            if (lifeTime < 60 * 5) {
                log.info("ticket:" + ticket + " is about to expire, left time: " + lifeTime + "s");
            }
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
