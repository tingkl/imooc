package com.mix.miaosha.redis;

// 抽象类定义共同的东西
public abstract class BasePrefix implements KeyPrefix {
    private int expiredSeconds;
    private String prefix;
    public BasePrefix(int expiredSeconds, String prefix) {
        this.expiredSeconds = expiredSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    @Override
    public int expiredSeconds() {
        // 默认0代表永不过期
        return this.expiredSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + this.prefix;
    }
}
