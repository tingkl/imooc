package com.mix.miaosha.rabbitmq;

import com.mix.miaosha.domain.entity.OrderInfo;
import com.mix.miaosha.domain.message.MiaoshaMessage;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.service.GoodsService;
import com.mix.miaosha.service.MiaoshaService;
import com.mix.miaosha.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    GoodsService goodsService;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message) {
        log.info("receive miaosha message:" + message);
        MiaoshaMessage mm = ConvertUtil.stringToBean(message, MiaoshaMessage.class);
        TokenUserVo user = mm.getUser();
        long goodsId = mm.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        // 减库存，下订单 写入秒杀订单
        // 减库存的时候还是要判断库存
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        if (orderInfo != null) {
            System.out.println(orderInfo);
        } else {
            System.out.println(CodeMsg.MIAO_SHA_FAIL);
        }
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("receive queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("receive queue2 message:" + message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeaders(byte[] message) {
        log.info("header queue message:" + new String(message) );
    }
}
