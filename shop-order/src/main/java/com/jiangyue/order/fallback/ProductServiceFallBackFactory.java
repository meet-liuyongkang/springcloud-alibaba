package com.jiangyue.order.fallback;

import com.jiangyue.order.sdk.ProductService;
import com.jiangyue.shop.common.entity.Product;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liuyongkang
 * @date Create in 2022/10/12 10:07
 *
 * 如果想要拿到因为调用异常时的异常信息，则可以使用下面降级方法
 */
@Component
@Slf4j
public class ProductServiceFallBackFactory implements FallbackFactory<ProductService> {
    @Override
    public ProductService create(Throwable throwable) {
        return new ProductService() {
            @Override
            public Product findById(Integer pid) {

                log.error("获取异常的降级执行。。。异常信息：" + throwable.getMessage());

                Product product = new Product();
                product.setPid(-1);
                product.setPname("我是可以获取异常的降级方法");
                return product;
            }
        };
    }
}
