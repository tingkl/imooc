package www.x.com.util;

/**
 * Created by tingkl on 2017/9/11.
 */
public class SSOCheck {

    public static final String USERNAEM = "user";
    public static final String PASSWORD = "123";
    private static String token;

    public static boolean checkLogin(String username, String password) {
        if (username.equals(USERNAEM) && password.equals(PASSWORD)) {
            return true;
        }
        return false;
    }

    public static String getToken() {
        token = "" + Math.round(Math.random() * 10000);
        return token;
    }

    public static boolean checkCookie (String cookieName, String cookieValue) {
        if (cookieName.equals("token") && cookieValue.equals(token)) {
            return true;
        }
        return false;
    }
}