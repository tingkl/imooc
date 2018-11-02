package com.mix.miaosha.controller;

import com.mix.miaosha.domain.User;
import com.mix.miaosha.result.CodeMsg;
import com.mix.miaosha.result.Result;
import com.mix.miaosha.service.RedisService;
import com.mix.miaosha.service.UserService;
import com.mix.miaosha.service.prefix.UserKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "tingkl");
        return "hello";
    }


    @GetMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @GetMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        Boolean success = userService.tx();
        return Result.success(success);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result redisGet() {
        Object v1 = redisService.get(UserKey.getById, "key1", Object.class);
        return Result.success(v1);
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result redisSet() {
        Boolean rs = redisService.set(UserKey.getById,"key2", 33);
        Long v2 = redisService.get(UserKey.getById,"key2", Long.class);
        return Result.success(v2);
    }

    @GetMapping("/redis/incr")
    @ResponseBody
    public Result redisIncr() {
        Long v2 = redisService.incr(UserKey.getById,"key4");
        return Result.success(v2);
    }

    @GetMapping("/redis/decr")
    @ResponseBody
    public Result redisDecr() {
        Long v2 = redisService.decr(UserKey.getById,"key4");
        return Result.success(v2);
    }
}
