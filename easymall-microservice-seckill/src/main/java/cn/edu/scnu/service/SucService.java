package cn.edu.scnu.service;

import com.easymall.pojo.Success;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SucService {
    List<Success> queryAllSuccess(long seckillId);
}
