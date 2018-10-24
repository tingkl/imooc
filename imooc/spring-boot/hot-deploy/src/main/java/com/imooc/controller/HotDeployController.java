package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tingkl on 2017/11/16.
 */
@Controller
public class HotDeployController {
    @GetMapping("/say")
    public String say(HttpServletRequest request) {
        request.setAttribute("say", "Hello Imooc hot, devtools is good");
        return "imooc";
    }
}
