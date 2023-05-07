package cn.edu.scnu.controller;

import cn.edu.scnu.service.ProductService;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;
import com.easymall.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product/manage")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/pageManage")
    public EasyUIResult productPageQuery(Integer page, Integer rows) {
        return productService.productPageQuery(page, rows);
    }

    @RequestMapping("/item/{prodId}")
    public String queryById(@PathVariable String prodId) {
        return productService.queryById(prodId);
    }

    @RequestMapping("/save")
    public SysResult productSave(Product product) {
        try {
            productService.productSave(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }

    @RequestMapping("/update")
    public SysResult productUpdate(Product product) {
        try {
            productService.productUpdate(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }
}
