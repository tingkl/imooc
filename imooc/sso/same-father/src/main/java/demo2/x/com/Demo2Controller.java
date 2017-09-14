package demo2.x.com;

import demo2.x.com.util.Demo2Tool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tingkl on 2017/9/11.
 */
@Controller
@RequestMapping("/demo2")
public class Demo2Controller {

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(@CookieValue(value="ssocookie", required = false) String ssocookie) throws UnsupportedEncodingException {
        if (ssocookie !=null ) {
            String result = Demo2Tool.doGet("http://check.x.com:8081/sso/checkCookie", "ssocookie", ssocookie);
            System.out.println("demo2 result is:" + result);
            if (result.equals("1")) {
                return "demo2/main";
            }
        }
        return "redirect:http://check.x.com:8081/sso/doLogin?gotoUrl=" + URLEncoder.encode("http://demo2.x.com:8081/demo2/main", "utf-8");
    }
}
