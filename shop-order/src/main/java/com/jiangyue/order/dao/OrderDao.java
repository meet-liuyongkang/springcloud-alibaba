package com.jiangyue.order.dao;

import com.jiangyue.shop.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyongkang
 * @date Create in 2022/9/23 08:58
 */
public interface OrderDao extends JpaRepository<Order, Long> {
}
