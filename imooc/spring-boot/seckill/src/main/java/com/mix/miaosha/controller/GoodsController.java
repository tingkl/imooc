package com.mix.miaosha.controller;

import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.GoodsDetailVo;
import com.mix.miaosha.domain.vo.GoodsVo;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.service.GoodsService;
import com.mix.miaosha.service.MiaoshaUserService;
import com.mix.miaosha.redis.RedisService;
import com.mix.miaosha.redis.GoodsKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    // 把从cookie和redis中取出登录用户的代码放到了UserArgumentResolver中，通过WebConfig处理，如果注入TokenUserVo，则触发这段逻辑
//    @GetMapping("/to_list")
//    public String toList(@CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required=false) String cookieToken,
//                         // 前端有可能直接把token当做参数，也可能啥也不做，还是cookie
//                         @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required=false) String paramToken,
//                         Model model,
//                         HttpServletResponse response
//                         ) {
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response, token);
//        model.addAttribute("user", miaoshaUser);
//        return "goods_list";
//    }

//    @GetMapping("/to_list")
//    public String toList(Model model, TokenUserVo user) {
//        List<GoodsVo> goodsList = goodsService.listGoodsVo();
//        model.addAttribute("user", user);
//        model.addAttribute("goodsList", goodsList);
//        return "goods_list";
//    }
    @GetMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, TokenUserVo user) {
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isEmpty(html)) {
            // 手动渲染
            List<GoodsVo> goodsList = goodsService.listGoodsVo();
            model.addAttribute("user", user);
            model.addAttribute("goodsList", goodsList);
            WebContext springWebContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
            html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
            if (!StringUtils.isEmpty(html)) {
                redisService.set(GoodsKey.getGoodsList, "", html);
            }
        }
        return html;
    }

    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result detail(TokenUserVo user, @PathVariable("goodsId")Long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        System.out.println(goods.toString());
        System.out.println(goods.getStartDate());
        System.out.println(new Date());
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus;
        int remainSeconds = 0;
        if (now < startAt) {
            // 秒杀没开始
            remainSeconds = (int) ((startAt - now) / 1000);
            miaoshaStatus = 0;
        } else if (now > endAt) {
            // 秒杀结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            // 秒杀进行中
            miaoshaStatus = 1;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }
}
