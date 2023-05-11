package cn.edu.scnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@SpringBootApplication
@RestController
public class JedisClusterTest {
    @Autowired
    private JedisCluster cluster;

    public static void main(String[] args) {
        SpringApplication.run(JedisClusterTest.class, args);
    }

    // 请求测试redis的读写功能,并且高可用代码逻辑
    @RequestMapping("cluster")
    public String setAndGet(String key, String value) {
        cluster.set(key, value);
        return cluster.get(key);
    }
}