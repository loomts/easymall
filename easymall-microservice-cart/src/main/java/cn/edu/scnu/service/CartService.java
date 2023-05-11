package cn.edu.scnu.service;

import com.easymall.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> queryMyCart(String userId);

    void cartSave(Cart cart);

    void updateNum(Cart cart);

    void deleteCart(Cart cart);
}
