package com.jiangyue.product.service;

import com.jiangyue.shop.common.entity.Product;

/**
 * @author liuyongkang
 * @date Create in 2022/9/22 14:41
 */
public interface ProductService {

    /**
     * 根据id查询商品
     * @param pid
     * @return
     */
    Product findById(Integer pid);
}
