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
import com.easymall.utils.PrefixKey;
import com.easymall.vo.EasyUIResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    JedisCluster jedis;

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
    public Product queryById(String productId) {
        String productKey = PrefixKey.PRODUCT_QUERY + productId;
        String lock = PrefixKey.PRODUCT_UPDATE + productId + PrefixKey.LOCK;
        try {
            if (jedis.exists(lock)) {
                return productMapper.queryById(productId);
            }
            if (jedis.exists(productId)) {
                String productJson = jedis.get(productKey);
                log.info("商品" + productId + "击中缓存");
                return JSON.parseObject(productJson, Product.class);
            } else {
                Product product = productMapper.queryById(productId);
                String productJson = JSON.toJSONString(product);
                jedis.setex(productKey, 60 * 60 * 24 * 2, productJson);
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void productSave(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productMapper.productSave(product);
    }

    @Override
    public void productUpdate(Product product) {
        String lock = PrefixKey.PRODUCT_UPDATE + product.getProductId() + PrefixKey.LOCK;
        long leftTime = jedis.ttl(PrefixKey.PRODUCT_QUERY + product.getProductId());
        jedis.setex(lock, (int) (leftTime), "");
        jedis.del(PrefixKey.PRODUCT_QUERY + product.getProductId());
        productMapper.productUpdate(product);
        jedis.del(lock);
    }
}
