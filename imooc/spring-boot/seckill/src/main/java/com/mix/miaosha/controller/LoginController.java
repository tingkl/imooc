package com.mix.miaosha.controller;

import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.LoginVo;
import com.mix.miaosha.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @GetMapping("/to_login")

    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        //ce21b747de5af71ab5c2e20ff0a60eea
        //d3b1294a61a07da9b49b6e22b2cbd7f9
        //24ba25942bdc7c2b9e931c515b99d359
        log.info(loginVo.toString());
//        MiaoshaUser user = miaoshaUserService.login(response, loginVo);
        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);
    }
}
