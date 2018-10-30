package com.mix.tpl.web;

import com.mix.tpl.entity.Pack;
import com.mix.tpl.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 获取所有的区域信息
     *
     * @return
     */
    @GetMapping(value = "/pack")
    private Pack pack() {
        User user = new User();
        return new Pack(user);
    }
}
