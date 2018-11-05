package com.mix.miaosha.redis;

// 定义接口
public interface KeyPrefix {
    public int expiredSeconds();
    public String getPrefix();
}
