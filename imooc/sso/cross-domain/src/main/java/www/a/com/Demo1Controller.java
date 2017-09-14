package www.a.com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import www.a.com.util.Demo1Tool;
import www.b.com.util.Demo2Tool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tingkl on 2017/9/11.
 */
@Controller
@RequestMapping("/demo1")
public class Demo1Controller {

    static List<String> hiddenUrl = new ArrayList();
    static {
        hiddenUrl.add("http://www.a.com:8081/demo1/addCookie");
        hiddenUrl.add("http://www.b.com:8081/demo2/addCookie");
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(@CookieValue(value="token", required = false) String token) throws UnsupportedEncodingException {
        if (token != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("cookieName", "token");
            map.put("cookieValue", token);
            String result = Demo2Tool.doGet("http://www.x.com:8081/sso/checkCookie", map);
            System.out.println("demo1 result is:" + result + "," + token);
            if (result.equals("1")) {
                return "demo1/main";
            }
        }
        return "demo1/login";
    }
    @RequestMapping(value = "/addCookie", method = RequestMethod.GET)
    public String addCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("demo1 add cookie");
        return "success";
    }
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username, String password, Model model) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        String result = Demo1Tool.doGet("http://www.x.com:8081/sso/doLogin", map);
        if (result.equals("0")) {
            return "demo1/login";
        } else {
            model.addAttribute("token", result);
            model.addAttribute("hiddenUrl", hiddenUrl);
            return "demo1/main";
        }
    }
}
