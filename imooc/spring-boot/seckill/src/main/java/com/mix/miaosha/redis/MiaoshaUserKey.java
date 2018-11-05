package com.mix.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {
    public static final int TOKEN_EXPIRD = 3600 * 24 *2;
    private MiaoshaUserKey(int expiredSeconds, String prefix) {
        super(expiredSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRD,"token" );
    public static MiaoshaUserKey getByMobile = new MiaoshaUserKey(0, "mobile");

}
