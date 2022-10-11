package com.jiangyue.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.jiangyue.product.service.ProductService;
import com.jiangyue.shop.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/9/22 14:45
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据id查询商品
     * @param pid
     * @return
     */
    @GetMapping("/findById/{pid}")
    public Product findById(@PathVariable Integer pid){
        Product product = productService.findById(pid);
        log.info("查询到商品：" + JSONObject.toJSONString(product));
        return product;
    }

}
