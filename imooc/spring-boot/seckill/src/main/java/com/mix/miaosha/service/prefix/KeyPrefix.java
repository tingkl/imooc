package com.mix.miaosha.service.prefix;

// 定义接口
public interface KeyPrefix {
    public int expiredSeconds();
    public String getPrefix();
}
