package com.mix.miaosha.domain.vo;

public class GoodsDetailVo {
    int miaoshaStatus;
    int remainSeconds;
    GoodsVo goods;
    TokenUserVo user;

    public TokenUserVo getUser() {
        return user;
    }

    public void setUser(TokenUserVo user) {
        this.user = user;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
}
