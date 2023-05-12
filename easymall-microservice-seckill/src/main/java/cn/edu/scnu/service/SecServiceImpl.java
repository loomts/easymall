package cn.edu.scnu.service;

import cn.edu.scnu.mapper.SecMapper;
import com.easymall.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecServiceImpl implements SecService {
    @Autowired
    private SecMapper secMapper;

    @Override
    public List<Seckill> queryAll() {
        return secMapper.queryAll();
    }

    @Override
    public Seckill queryOne(String seckillId) {
        return secMapper.queryOne(Long.parseLong(seckillId));
    }
}
