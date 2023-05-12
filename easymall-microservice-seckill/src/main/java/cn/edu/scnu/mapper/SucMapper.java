package cn.edu.scnu.mapper;

import com.easymall.pojo.Success;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SucMapper {
    void saveSuc(Success success);
    List<Success> queryAllSuccess(long seckillId);
}
