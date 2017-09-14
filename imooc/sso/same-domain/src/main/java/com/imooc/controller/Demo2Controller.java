package com.imooc.controller;

import com.imooc.util.SSOCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tingkl on 2017/9/11.
 */
@Controller
@RequestMapping("/demo2")
public class Demo2Controller {
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request) throws UnsupportedEncodingException {
        if (SSOCheck.checkCookie(request)) {
            return "demo2/main";
        }

        return "redirect:/sso/doLogin?gotoUrl=" + URLEncoder.encode("http://localhost:8081/demo2/main", "utf-8");
    }
}
