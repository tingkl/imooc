package com.mix.tpl.web;

import com.mix.tpl.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class ShiroController {
    private final static Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }
    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {
        logger.debug(username + ":" + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "login";
        }
    }
}
