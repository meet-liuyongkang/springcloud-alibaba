package com.jiangyue.order.service.impl;

import com.jiangyue.order.dao.OrderDao;
import com.jiangyue.order.service.OrderService;
import com.jiangyue.shop.common.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyongkang
 * @date Create in 2022/9/23 09:00
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id).get();
    }

    @Override
    public Order save(Order order) {
        Order order1 = orderDao.save(order);
        return order1;
    }

}
