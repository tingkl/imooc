package com.imooc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tingkl on 2017/9/11.
 */
public class SSOCheck {

    public static final String USERNAEM = "user";
    public static final String PASSWORD = "123";

    public static boolean checkLogin(String username, String password) {
        if (username.equals(USERNAEM) && password.equals(PASSWORD)) {
            return true;
        }
        return false;
    }


    public static boolean checkCookie (HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("ssocookie") && cookie.getValue().equals("sso")) {
                    return true;
                }
            }
        }
        return false;

    }
}
