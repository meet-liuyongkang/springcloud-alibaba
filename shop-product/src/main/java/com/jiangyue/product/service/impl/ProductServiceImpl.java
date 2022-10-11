package com.jiangyue.product.service.impl;

import com.jiangyue.product.dao.ProductDao;
import com.jiangyue.product.service.ProductService;
import com.jiangyue.shop.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyongkang
 * @date Create in 2022/9/22 14:43
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findById(Integer pid) {
        if (pid == -1){
            throw new RuntimeException("商品pid不存在！");
        }
        return productDao.findById(pid).get();
    }
}
