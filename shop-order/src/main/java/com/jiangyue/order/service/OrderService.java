package com.jiangyue.order.service;

import com.jiangyue.shop.common.entity.Order;

/**
 * @author liuyongkang
 * @date Create in 2022/9/23 09:00
 */
public interface OrderService {

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order findById(Long id);

    /**
     * 保存订单
     * @param order
     * @return
     */
    Order save(Order order);

}
