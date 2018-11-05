package com.mix.miaosha.util;

import org.springframework.util.DigestUtils;

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String inputPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
    public static void main(String [] args) {
        // 浏览器pass md5传服务器
        System.out.println(inputPassToFormPass("123456"));
        // 数据库存的是一个随机slat的md5，防止数据库被盗获取密码
        System.out.println(inputPassToDBPass("123456", "5a6b7c8d"));

        // String a = DigestUtils.md5DigestAsHex("123456".getBytes());
        // System.out.println(a);
    }
}
