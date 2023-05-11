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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.params.SetParams;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private JedisCluster jedis;

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
        try {
            user.setUserPassword(MD5Util.md5(user.getUserPassword()));
            User userInfo = userMapper.doLogin(user);
            if (userInfo == null) {
                return "";
            }
            String ticket = PrefixKey.USER_LOGIN_TICKET + user.getUserId() + System.currentTimeMillis();
            String userJson = JSON.toJSONString(userInfo);
            String loginCheckKey = PrefixKey.USER_LOGINED_CHECK_PREFIX + userInfo.getUserId();
            long len = jedis.llen(loginCheckKey);
            if (len >= 3) {
                jedis.lpop(loginCheckKey);
            }
            jedis.rpush(loginCheckKey, ticket);
//            String oldTicket = jedis.get(loginCheckKey);
//            if (oldTicket != null && !oldTicket.isEmpty()) {
//                jedis.del(oldTicket);
//            }
//            jedis.set(loginCheckKey, ticket);
            log.info("LOGIN " + userInfo.getUserName() + "::" + ticket);
            jedis.setex(ticket, 30 * 60, userJson);
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String queryUser(String ticket) {
        String userJson;
        try {
            //首先判断超时剩余时间
            Long leftTime = jedis.pttl(ticket);
            //少于10分钟,延长5分钟
            if (leftTime < 1000 * 60 * 10L) {
                jedis.pexpire(ticket, leftTime + 1000 * 60 * 5);
            }
            userJson = jedis.get(ticket);
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
