package cn.edu.scnu.service;

import cn.edu.scnu.mapper.UserMapper;
import com.alibaba.fastjson2.JSON;
import com.easymall.pojo.User;
import com.easymall.utils.MD5Util;
import com.easymall.utils.MapperUtil;
import com.easymall.utils.PrefixKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.params.SetParams;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ShardedJedisPool pool;

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
        ShardedJedis jedis = pool.getResource();
        try {
            user.setUserPassword(MD5Util.md5(user.getUserPassword()));
            User userInfo = userMapper.doLogin(user);
            if (userInfo == null) {
                return "";
            }
            String ticket = PrefixKey.USER_LOGIN_TICKET + user.getUserId() + System.currentTimeMillis();
            String userJson = JSON.toJSONString(userInfo);
            String loginCheckKey = PrefixKey.USER_LOGINED_CHECK_PREFIX + userInfo.getUserId();
            String oldTicket = jedis.get(loginCheckKey);
            if (oldTicket != null && !oldTicket.isEmpty()) {
                jedis.del(oldTicket);
            }
            jedis.set(loginCheckKey, ticket);
            log.info("LOGIN " + userInfo.getUserName() + "::" + ticket);
            jedis.set(ticket, userJson, SetParams.setParams().ex(15 * 60));
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String queryUser(String ticket) {
        ShardedJedis jedis = pool.getResource();
        String userJson;
        try {
            userJson = jedis.get(ticket);
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
