package cn.edu.scnu.service;

import cn.edu.scnu.mapper.CartMapper;
import com.easymall.pojo.Cart;
import com.easymall.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Cart> queryMyCart(String userId) {
        log.info("查询购物车信息，用户id为：{}", userId);
        return cartMapper.queryMyCart(userId);
    }

    @Override
    public void cartSave(Cart cart) {
        Cart exist = cartMapper.queryOne(cart);
        if (exist != null) {
            exist.setNum(exist.getNum() + cart.getNum());
            cartMapper.updateNum(exist);
        } else {
            Product product = restTemplate.getForObject("http://product-service/product/manage/item/" + cart.getProductId(), Product.class);
            cart.setProductPrice(product.getProductPrice());
            cart.setProductName(product.getProductName());
            cart.setProductImage(product.getProductImgurl());
            cartMapper.saveCart(cart);
        }
    }

    @Override
    public void updateNum(Cart cart) {
        cartMapper.updateNum(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartMapper.deleteCart(cart);
    }


}
