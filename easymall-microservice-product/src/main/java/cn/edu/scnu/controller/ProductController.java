package cn.edu.scnu.controller;

import cn.edu.scnu.service.ProductService;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;
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
    public EasyUIResult productPageQuery(Integer page,Integer rows){
        return productService.productPageQuery(page,rows);
    }
    @RequestMapping("/item/{prodId}")
    public Product queryById(@PathVariable String prodId){
        return productService.queryById(prodId);
    }
}
