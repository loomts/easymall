package cn.edu.scnu.service;

import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;

import java.util.UUID;


public interface ProductService {
    EasyUIResult productPageQuery(Integer page, Integer rows);
    Product queryById(String prodId);
    void productSave(Product product);
    void productUpdate(Product product);
}
