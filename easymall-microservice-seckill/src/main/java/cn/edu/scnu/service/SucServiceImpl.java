package cn.edu.scnu.service;

import cn.edu.scnu.mapper.SucMapper;
import com.easymall.pojo.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucServiceImpl implements SucService {
    @Autowired
    SucMapper sucMapper;

    @Override
    public List<Success> queryAllSuccess(long seckillId) {
        return sucMapper.queryAllSuccess(seckillId);
    }
}
