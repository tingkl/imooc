package com.mix.miaosha.controller;

import com.mix.miaosha.domain.result.Result;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/info")
    @ResponseBody
    public Result<TokenUserVo> info(TokenUserVo user) {
        return Result.success(user);
    }

    @GetMapping("/effectedRow")
    @ResponseBody
    public Result effectedRow(@RequestParam("name") String name) {
        int ret = userService.effectedRow(name);
        return Result.success(ret);
    }

    @GetMapping("/effectedRow-reduce")
    @ResponseBody
    public Result effectedRow() {
        int ret = userService.effectedRowReduce();
        return Result.success(ret);
    }
}
