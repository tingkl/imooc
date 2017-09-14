package check.x.com;

import check.x.com.util.SSOCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tingkl on 2017/9/11.
 */
@Controller
@RequestMapping("/sso")
public class SSOController {

    /*
    *
    * sso类型2： 同父域SSO
    *
    * 例子： http://demo1.x.com:8081/demo1
    *       http://demo2.x.com:8081/demo2
    * */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username, String password, String gotoUrl, HttpServletResponse response, Model model) {
        boolean ok = SSOCheck.checkLogin(username, password);
        if (ok) {
            Cookie cookie = new Cookie("ssocookie", "sso");
            cookie.setMaxAge(30 * 60);// 设置为30min
            // cookie设置到父域
            cookie.setDomain(".x.com");
            // cookie设置路径顶层
            cookie.setPath("/");
            // 设置到父域的顶层
            System.out.println("已添加===============" + gotoUrl);
            response.addCookie(cookie);
            model.addAttribute("gotoUrl", gotoUrl);
            return "success";
        }
        return "fail";

    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String doLogin(Model model, String gotoUrl) {
        model.addAttribute("gotoUrl", gotoUrl);
        System.out.println("get doLogin," + gotoUrl);
        return "login";
    }

    @RequestMapping(value = "/checkCookie")
    @ResponseBody
    public String checkCookie(String cookieName, String cookieValue) {
        if (SSOCheck.checkCookie(cookieName, cookieValue)) {
            return "1";
        }
        return "0";
    }

}
