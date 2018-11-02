package com.mix.miaosha.service.prefix;

public class OrderKey extends BasePrefix {
    public OrderKey(int expiredSeconds, String prefix) {
        super(expiredSeconds, prefix);
    }
}
