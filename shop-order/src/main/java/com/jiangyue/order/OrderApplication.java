package com.jiangyue.order;

import com.jiangyue.order.sdk.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;
import java.util.Map;

/**
 * @author liuyongkang
 * @date Create in 2022/9/22 17:12
 */
@SpringBootApplication
@EntityScan("com.jiangyue.shop.common.entity")
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);

        Map<String, ProductService> beansOfType = applicationContext.getBeansOfType(ProductService.class);
        Iterator<String> iterator = beansOfType.keySet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
