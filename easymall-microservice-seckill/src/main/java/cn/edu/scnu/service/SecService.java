package cn.edu.scnu.service;

import com.easymall.pojo.Seckill;

import java.util.List;

public interface SecService {
    List<Seckill> queryAll();
    Seckill queryOne(String seckillId);
}
