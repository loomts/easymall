/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:27:51 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import cn.edu.scnu.mapper.ProductMapper;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    public EasyUIResult productPageQuery(Integer page, Integer rows) {
        EasyUIResult result = new EasyUIResult();
        Integer total = productMapper.queryTotal();
        Integer start = (page - 1) * rows;
        List<Product> list = productMapper.queryByPage(start, rows);
        result.setTotal(total);
        result.setRows(list);
        return result;
    }

    @Override
    public Product queryById(String prodId) {
        return productMapper.queryById(prodId);
    }

    @Override
    public void productSave(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productMapper.productSave(product);
    }

    @Override
    public void productUpdate(Product product) {

    }
}

