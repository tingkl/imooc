package com.imooc.controller;

import com.imooc.util.SSOCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    * sso类型1： 同域SSO
    *
    * 例子： http://localhost/demo1/main.jsp
    *       http://localhost/demo2/main.jsp
    * */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username, String password, String gotoUrl, HttpServletResponse response, Model model) {
        boolean ok = SSOCheck.checkLogin(username, password);
        if (ok) {
            Cookie cookie = new Cookie("ssocookie", "sso");
            cookie.setMaxAge(30 * 60);// 设置为30min
            //  cookie设置路径顶层，demo1和demo2都可以见
            cookie.setPath("/");
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

}
