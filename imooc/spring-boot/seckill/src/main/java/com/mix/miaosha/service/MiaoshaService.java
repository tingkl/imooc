package com.mix.miaosha.service;

import com.mix.miaosha.domain.entity.OrderInfo;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(TokenUserVo user, GoodsVo goods) {
        // 减库存 下订单 写入秒杀订单
        int ret = goodsService.reduceStock(goods);
        // order_info miaosha_order
        OrderInfo orderInfo = null;
        if (ret > 0) {
            orderInfo = orderService.createOrder(user, goods);
        }
        return orderInfo;
    }
}
