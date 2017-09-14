package com.imooc.controller;

import com.imooc.object.Admin;
import com.imooc.object.User;
import com.imooc.object.UserListForm;
import com.imooc.object.UserMapForm;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tingkl on 2017/8/7.
 */
@Controller
public class TestController {

    @RequestMapping("baseType")
    @ResponseBody
    // age不可以空，必须传
    public String baseType(int age) {
        return "age:" + age;
    }

    @RequestMapping("baseType2")
    @ResponseBody
    // 包装类型，age可以不传为null
    public String baseType2(Integer age) {
        return "age:" + age;
    }

    @RequestMapping("array")
    @ResponseBody
    // http://localhost:8081/array?name=tom&name=jerry&name=tingkl
    public String array(String[] name) {
        StringBuilder sb = new StringBuilder();
        for (String item : name) {
            sb.append(item).append(" ");
        }
        return sb.toString();
    }

    @RequestMapping("object")
    @ResponseBody
    // http://localhost:8081/object?name=tom&age=10&contactInfo.phone=10086&contactInfo.address=shanghai
    public String object(User user, Admin admin) {
        // User{name='tom', age=10, contactInfo=ContactInfo{phone='10086', address='shanghai'}} Admin{name='tom', age=10}
        return user.toString() + "\n" + admin.toString();
    }


    // http://localhost:8081/object?user.name=tom&age=10&contactInfo.phone=10086&contactInfo.address=shanghai&admin.name=tingkl
    // User{name='tom', age=10, contactInfo=ContactInfo{phone='10086', address='shanghai'}} Admin{name='tingkl', age=10}
    @InitBinder("user")
    public void initUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user.");
    }

    @InitBinder("admin")
    public void initAdmin(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("admin.");
    }


    @RequestMapping("list")
    @ResponseBody
    // 不能直接用 List<User> users来接收，必须包一层
    // 就像用object，也是吧name和age塞到User里面
    public String list(UserListForm userListForm) {
        // http://localhost:8081/list?users[0].name=tom&users[1].name=tingkl&users[9].name=yark

        /*
        *listSize:10 UserListForm{users=[User{name='tom', age=null, contactInfo=null}, User{name='tingkl', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='null', age=null, contactInfo=null}, User{name='yark', age=null, contactInfo=null}]}
        * */
        return "listSize:" + userListForm.getUsers().size() + " " + userListForm.toString();
    }

    @RequestMapping("map")
    @ResponseBody
    public String map(UserMapForm userMapForm) {
        // http://localhost:8081/map?users["x"].name=tom&users["y"].name=jerry
        // http://localhost:8081/map?users['x'].name=tom&users['y'].name=jerry
        // UserMapForm{users={x=User{name='tom', age=null, contactInfo=null}, y=User{name='jerry', age=null, contactInfo=null}}}
        return userMapForm.toString();
    }

    @RequestMapping(value = "json", method = RequestMethod.POST)
    @ResponseBody
    public User json(@RequestBody User user) {
        return user;
    }

    @RequestMapping("converter")
    //true yes on 1
    @ResponseBody
    public String converter(Boolean bool) {
        return bool.toString();
    }


    @RequestMapping("date1")
    @ResponseBody
    // http://localhost:8081/date1?date1=2018-01-01
    public String date1(Date date1) {
        return date1.toString();
    }


    // 只能局部
    @InitBinder("date1")
    public void initDate1(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }


    @RequestMapping("date2")
    @ResponseBody
    // http://localhost:8081/date2?date2=2018-01-01
    public String date2(Date date2) {
        return date2.toString();
    }
}
