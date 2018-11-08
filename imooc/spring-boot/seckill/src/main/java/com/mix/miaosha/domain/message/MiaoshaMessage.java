package com.mix.miaosha.domain.message;

import com.mix.miaosha.domain.vo.TokenUserVo;

public class MiaoshaMessage {
    private TokenUserVo user;
    private long goodsId;

    public TokenUserVo getUser() {
        return user;
    }

    public void setUser(TokenUserVo user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
