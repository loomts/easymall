package cn.edu.scnu.mapper;

import com.easymall.pojo.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    @Select("select * from t_cart where user_id = #{userId} and product_id = #{productId}")
    Cart queryOne(Cart cart);

    @Update("update t_cart set num = #{num} where user_id = #{userId} and product_id = #{productId}")
    void updateNum(Cart cart);

    @Delete("delete from t_cart where user_id = #{userId} and product_id = #{productId}")
    void deleteCart(Cart cart);

    @Select("select * from t_cart where user_id = #{userId}")
    List<Cart> queryMyCart(String userId);

    @Insert("insert into t_cart(user_id,product_id,product_name,product_image,product_price,num) values(#{userId},#{productId},#{productName},#{productImage},#{productPrice},#{num})")
    void saveCart(Cart cart);
}
