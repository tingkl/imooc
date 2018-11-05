package com.mix.miaosha.controller;

import com.mix.miaosha.domain.entity.OrderInfo;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.OrderDetailVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.service.GoodsService;
import com.mix.miaosha.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderDetail(TokenUserVo user, @RequestParam("orderId")Long orderId) {
        OrderInfo order = orderService.getOrderInfoByOrderInfoId(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }
}

