package cn.edu.scnu;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;
public class JedisTest {
    @Autowired
    private JedisCluster cluster;

    @Test
    public void test01() {
        //收集节点信息,至少提供一个节点的连接信息
        Set<HostAndPort> clusterSet = new HashSet<HostAndPort>();
        //将至少一个节点传递给set对象
        clusterSet.add(new HostAndPort("116.205.130.21", 8005));
        //底层通过连接的节点获取集群全部节点信息
        //配置连接池的配置对象
        GenericObjectPoolConfig config =
                new GenericObjectPoolConfig();
        config.setMaxTotal(200);
        //构造一个JedisCluster
        JedisCluster cluster = new JedisCluster(clusterSet, config);
        cluster.set("gender", "male");
        System.out.println(cluster.get("gender"));
    }

    @Test
    public void test02() {
        cluster.set("name", "dakta");
        System.out.println(cluster.get("name"));
    }

    @Test
    public void test03() {
        // 收集节点信息
        Set<HostAndPort> set = new HashSet<>();
        // 将3个节点信息.add到infoList总
        set.add(new HostAndPort("116.205.130.21", 8000));
        set.add(new HostAndPort("116.205.130.21", 8001));
        set.add(new HostAndPort("116.205.130.21", 8002));
        // 创建一个连接池的配置对象,定义最大连接数
        // 最小空闲,最大空闲,最大等待时长
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(8);
        config.setMinIdle(2);
        // 配置对象.收集对象创建连接池
        // 每次操作redis集群时,从pool来获取资源
        try (JedisCluster jedisCluster = new JedisCluster(set, config)) {
            jedisCluster.set("location", "华南师范大学南海校区");
            System.out.println(jedisCluster.get("location"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.add(new HostAndPort("116.205.130.21", 28000).toString());
        sentinelSet.add(new HostAndPort("116.205.130.21", 28001).toString());
        sentinelSet.add(new HostAndPort("116.205.130.21", 28002).toString());

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(200);

        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinelSet, config);

        Jedis jedis = pool.getResource();

        System.out.println("当前正在服役的主节点:" + pool.getCurrentHostMaster());

        String name = jedis.get("name");
        System.out.println("name:" + name);
    }
}


