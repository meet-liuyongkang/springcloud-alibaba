package com.jiangyue.order.controller;

import com.jiangyue.order.sdk.ProductService;
import com.jiangyue.order.service.OrderService;
import com.jiangyue.shop.common.entity.Order;
import com.jiangyue.shop.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyongkang
 * @date Create in 2022/9/23 09:03
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private OrderService orderService;

    @Qualifier("com.jiangyue.order.sdk.ProductService")
    @Autowired
    private ProductService productService;

    @GetMapping("/prod/{pid}")
    public Order addOrder(@PathVariable Integer pid) throws InterruptedException {
        log.info("1.客户下单，这时候要调用商品微服务查询商品信息");


        // 1.手动随机选择服务器调用
//        List<ServiceInstance> productInstances = discoveryClient.getInstances("service-product");
//        int nextInt = RandomUtils.nextInt(productInstances.size());
//        ServiceInstance serviceInstance = productInstances.get(nextInt);
//        String productUrl = serviceInstance.getHost() + ":" + serviceInstance.getPort();
//        log.info("从nacos获取到商品服务的地址：" + productUrl);
//        Product product = restTemplate.getForObject("http://" + productUrl + "/product/findById/" + pid, Product.class);


        // 2.使用ribbon负载均衡，只需要在RestTemplate对象加上@LoadBalance
//        String serviceName = "product-service";
//        Product product = restTemplate.getForObject("http://" + serviceName + "/product/findById/" + pid, Product.class);


        // 3.使用 feign 调用服务
        Product product = productService.findById(pid);

        // 睡眠1秒，模拟业务处理时间，测试高并发场景
        Thread.sleep(1000);


        log.info("查询到的商品结果：" + product.toString());


        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);

        // 高并发下，不保存到数据库
//        Order order1 = orderService.save(order);

        return order;
    }


    @GetMapping("/message")
    public String message(){
        return RandomStringUtils.random(1);
    }
}
