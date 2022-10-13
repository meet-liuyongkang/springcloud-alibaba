package com.jiangyue.order.fallback;

import com.jiangyue.order.sdk.ProductService;
import com.jiangyue.shop.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liuyongkang
 * @date Create in 2022/10/11 16:20
 *
 * feign整合sentinel实现容错降级
 */
@Component
@Slf4j
public class ProductServiceFallBack implements ProductService {

    @Override
    public Product findById(Integer pid) {
        log.info("findById feign降级方法调用成功");
        return new Product();
    }
}
