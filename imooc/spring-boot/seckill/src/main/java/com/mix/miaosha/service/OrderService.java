package com.mix.miaosha.service;

import com.mix.miaosha.dao.OrderDao;
import com.mix.miaosha.domain.entity.MiaoshaOrder;
import com.mix.miaosha.domain.entity.OrderInfo;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.redis.OrderKey;
import com.mix.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        System.out.println("from redis");
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);
        // return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    /*
     * ALTER TABLE `miaosha`.`miaosha_order` ADD UNIQUE INDEX `goodsIdUserId` (`user_id` ASC, `goods_id` ASC);
     * */
    public OrderInfo createOrder(TokenUserVo user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        Long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + "_" + goods.getId(), miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderInfoByOrderInfoId(Long orderInfoId) {
        return orderDao.getOrderInfoByOrderInfoId(orderInfoId);
    }
}
