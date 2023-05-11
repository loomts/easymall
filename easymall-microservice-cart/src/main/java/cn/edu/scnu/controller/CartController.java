package cn.edu.scnu.controller;

import cn.edu.scnu.service.CartService;
import com.easymall.pojo.Cart;
import com.easymall.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/query")
    public List<Cart> queryMyCart(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        return cartService.queryMyCart(userId);
    }

    @RequestMapping("/save")
    public SysResult cartSave(Cart cart) {
        try {
            cartService.cartSave(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }

    @GetMapping("/update")
    public SysResult updateNum(Cart cart) {
        try {
            cartService.updateNum(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }

    @GetMapping("/delete")
    public SysResult deleteCart(Cart cart) {
        try {
            cartService.deleteCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }


}
