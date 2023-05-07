/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:27:51 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import cn.edu.scnu.mapper.ProductMapper;
import com.alibaba.fastjson2.JSON;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    StringRedisTemplate redis;

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
    public String queryById(String prodId) {
        String productInfo = redis.opsForValue().get(prodId);
        if (productInfo != null && !productInfo.isEmpty()) {
            log.info("商品" + prodId + "击中缓存");
            return productInfo;
        } else {
            Product product = productMapper.queryById(prodId);
            String productJson = JSON.toJSONString(product);
            redis.opsForValue().set(prodId, productJson);
            return productJson;
        }
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

