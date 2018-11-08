package com.mix.miaosha.redis;

public class GoodsKey extends BasePrefix {
    public static final int TOKEN_EXPIRD = 3600 * 24 *2;
    private GoodsKey(int expiredSeconds, String prefix) {
        super(expiredSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "getGoodsList" );
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "getMiaoshaGoodsStock" );

}
