package cn.edu.scnu.mapper;

import com.easymall.pojo.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SecMapper {
    List<Seckill> queryAll();

    Seckill queryOne(long seckillId);

    int updateNum(@Param("seckillId") Long seckillId, @Param("nowTime") Date nowTime);
}
