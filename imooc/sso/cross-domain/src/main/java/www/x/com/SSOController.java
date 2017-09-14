package www.x.com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import www.x.com.util.SSOCheck;

/**
 * Created by tingkl on 2017/9/11.
 */
@Controller
@RequestMapping("/sso")
public class SSOController {
    /*
    *
    * sso类型3： 跨域SSO
    *
    * 例子： http://www.a.com:8081/demo1
    *       http://www.b.com:8081/demo2
    * */
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    @ResponseBody
    public String doLogin(String username, String password) {
        boolean ok = SSOCheck.checkLogin(username, password);
        if (ok) {
            String token = SSOCheck.getToken();
            return token;
        }
        return "0";

    }

    @RequestMapping(value = "/checkCookie")
    @ResponseBody
    public String checkCookie(String cookieName, String cookieValue) {
        System.out.println("checkCookie" +" ," + cookieName + "," + cookieValue);
        if (SSOCheck.checkCookie(cookieName, cookieValue)) {
            return "1";
        }
        return "0";
    }

}
