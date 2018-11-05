package com.mix.miaosha.domain;

import com.alibaba.fastjson.JSON;

public abstract class Base {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
