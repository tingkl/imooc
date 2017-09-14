package check.x.com.util;

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


    public static boolean checkCookie (String cookieName, String cookieValue) {
        if (cookieName.equals("ssocookie") && cookieValue.equals("sso")) {
            return true;
        }
        return false;
    }
}