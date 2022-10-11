package com.jiangyue.shop.common.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author liuyongkang
 * @date Create in 2022/9/20 16:17
 */
@Entity(name = "shop_product")
@Data
public class Product {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    /**
     * 商品名称
     */
    private String pname;

    /**
     * 商品价格
     */
    private Double pprice;

    /**
     * 库存
     */
    private Integer stock;

}
