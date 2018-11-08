package com.mix.miaosha.controller;

import com.mix.miaosha.access.AccessLimit;
import com.mix.miaosha.domain.entity.MiaoshaOrder;
import com.mix.miaosha.domain.entity.MiaoshaUser;
import com.mix.miaosha.domain.message.MiaoshaMessage;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.rabbitmq.MQSender;
import com.mix.miaosha.redis.GoodsKey;
import com.mix.miaosha.redis.MiaoshaKey;
import com.mix.miaosha.redis.OrderKey;
import com.mix.miaosha.redis.RedisService;
import com.mix.miaosha.service.GoodsService;
import com.mix.miaosha.service.MiaoshaService;
import com.mix.miaosha.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    RedisService redisService;
    private Map<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();


    /*
     * 系统初始化
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化后回调
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList != null) {
            for (GoodsVo goods : goodsVoList) {
                redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
                localOverMap.put(goods.getId(), false);
            }
        }
    }

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender mqSender;


    /*
    * orderId: 成功
    * -1: 秒杀失败
    * 0: 排队中
    * */
    @AccessLimit(seconds=5, maxCount=10, needLogin=true)
    @GetMapping("/result")
    @ResponseBody
    public Result miaoshaResult(TokenUserVo user, @RequestParam("goodsId") Long goodsId) {
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

    @AccessLimit(seconds=50, maxCount=5, needLogin=true)
    @RequestMapping(value="/path", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(TokenUserVo user,
                                         @RequestParam("goodsId")long goodsId,
                                         @RequestParam(value="verifyCode", defaultValue="0")int verifyCode
    ) {
        boolean check = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if(!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path  =miaoshaService.createMiaoshaPath(user, goodsId);
        return Result.success(path);
    }
    @PostMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result doMiaosha(TokenUserVo user, @RequestParam("goodsId") Long goodsId, @PathVariable("path") String path) {
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if(!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock <= 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAO_SHA);
        }

        // 入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(miaoshaMessage);
        // 0 代表排队中
        return Result.success(0);
    }
    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, TokenUserVo user,
                                              @RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image  = miaoshaService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAO_SHA_FAIL);
        }
    }


//    @PostMapping("/do_miaosha")
//    @ResponseBody
//    public Result<OrderInfo> doMiaosha(TokenUserVo user, @RequestParam("goodsId")Long goodsId) {
//        // 判断库存
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock <= 0) {
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }
//        // 判断是否已经秒杀到了
//        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {
//            return Result.error(CodeMsg.REPEAT_MIAO_SHA);
//        }
//        // 减库存，下订单 写入秒杀订单
//        // 减库存的时候还是要判断库存
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        if (orderInfo !=null) {
//            return Result.success(orderInfo);
//        } else {
//            return Result.error(CodeMsg.MIAO_SHA_FAIL);
//        }
//    }
}
