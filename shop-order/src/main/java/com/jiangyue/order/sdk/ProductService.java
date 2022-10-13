package com.jiangyue.order.sdk;

import com.jiangyue.order.fallback.ProductServiceFallBackFactory;
import com.jiangyue.shop.common.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liuyongkang
 * @date Create in 2022/9/23 16:54
 *
 * 商品服务SDK
 *
 * 注意：FallBack 不能获取异常，FallBackFactory可以获取异常，但是他们不能同时使用
 */
@FeignClient(value = "product-service",
        //fallback = ProductServiceFallBack.class
        fallbackFactory = ProductServiceFallBackFactory.class)
public interface ProductService {

    /**
     * 根据id查找商品
     * @param pid
     * @return
     */
    @GetMapping("/product/findById/{pid}")
    Product findById(@PathVariable Integer pid);

}
