package com.mix.miaosha.controller;

import com.mix.miaosha.domain.entity.MiaoshaOrder;
import com.mix.miaosha.domain.entity.OrderInfo;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.service.GoodsService;
import com.mix.miaosha.service.MiaoshaService;
import com.mix.miaosha.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @PostMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> doMiaosha(TokenUserVo user, @RequestParam("goodsId")Long goodsId) {
        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAO_SHA);
        }
        // 减库存，下订单 写入秒杀订单
        // 减库存的时候还是要判断库存
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        if (orderInfo !=null) {
            return Result.success(orderInfo);
        } else {
            return Result.error(CodeMsg.MIAO_SHA_FAIL);
        }
    }
}
