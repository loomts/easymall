/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:28:22 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.mapper;

import com.easymall.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper{
    Integer queryTotal();
    List<Product> queryByPage(@Param("start")Integer start,@Param("rows")Integer rows);
    Product queryById(String prodId);
    void productSave(Product product);
    void productUpdate(Product product);
}
