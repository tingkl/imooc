package com.mix.miaosha.service;

import com.mix.miaosha.dao.MiaoshaUserDao;
import com.mix.miaosha.domain.entity.MiaoshaUser;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.vo.LoginVo;
import com.mix.miaosha.exception.GlobalException;
import com.mix.miaosha.redis.MiaoshaUserKey;
import com.mix.miaosha.redis.RedisService;
import com.mix.miaosha.util.MD5Util;
import com.mix.miaosha.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    public MiaoshaUser getByMobile(String mobile) {
        // 取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getByMobile, mobile, MiaoshaUser.class);
        if (user == null) {
            user = miaoshaUserDao.getByMobile(mobile);
        }
        if (user != null) {
            redisService.set(MiaoshaUserKey.getByMobile, mobile, user);
        }
        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        MiaoshaUser user = getByMobile(mobile);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // user并没有存到服务器相关的session中，而是存放在一个统一的容器（redis）中，
        // 通过cookie来获取信息，达到分布式session的效果
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    // 获取数据，延长token过期时间
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expiredSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
