package com.mix.tpl.web;

import com.mix.tpl.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class ShiroController {
    private final static Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @GetMapping("/logout")
    public String logout() {
        System.out.println("logout");
        logger.warn("logout");
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:login";
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("admin");
        return "admin";
    }

    @GetMapping("/demo")
    public String demo() {
        System.out.println("demo");
        return "demo";
    }

    @GetMapping("/edit")
    public String edit() {
        System.out.println("edit");
        return "edit";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("login");
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        System.out.println("index");
        return "index";
    }

    @GetMapping("/test")
    public String test() {
        return "admin";
    }

    @GetMapping("/user")
    // @Controller需要加入@ResponseBody，@RestController则不需要
    @ResponseBody
    public User user() {
        User user = new User();
        try {
            Subject subject = SecurityUtils.getSubject();
            user = (User) subject.getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {
        System.out.println("loginUser----");
        logger.debug(username + ":" + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            System.out.println("redirect:index");
            logger.debug("redirect:index");
            return "redirect:index";
        } catch (Exception e) {
            return "redirect:login";
        }
    }
}
