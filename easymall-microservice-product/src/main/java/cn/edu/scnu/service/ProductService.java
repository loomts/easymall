package cn.edu.scnu.service;

import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;


public interface ProductService {
    EasyUIResult productPageQuery(Integer page, Integer rows);

    Product queryById(String prodId);
}
