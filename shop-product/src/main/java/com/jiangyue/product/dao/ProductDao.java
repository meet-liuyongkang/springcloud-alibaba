package com.jiangyue.product.dao;

import com.jiangyue.shop.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyongkang
 * @date Create in 2022/9/22 14:39
 */
public interface ProductDao extends JpaRepository<Product,Integer> {

}
