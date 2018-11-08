package com.mix.miaosha.access;

import com.mix.miaosha.domain.vo.TokenUserVo;

// 线程安全，跟当前线程绑定的
public class UserContext {
    private static ThreadLocal<TokenUserVo> userHolder = new ThreadLocal<TokenUserVo>();

    public static void setUser(TokenUserVo user) {
        userHolder.set(user);
    }

    public static TokenUserVo getUser() {
        return userHolder.get();
    }
}
