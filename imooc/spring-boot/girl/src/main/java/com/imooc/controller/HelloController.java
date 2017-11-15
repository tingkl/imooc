package com.imooc.controller;

import com.imooc.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tingkl on 2017/11/15.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Value("${cupSize}")
    private String cupSize;
    @Value("${age}")
    private Integer age;
    @Value("${content}")
    private String content;

    @Autowired
    private GirlProperties girlProperties;
    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say() {
        return "Hello Spring Boot!" + cupSize + ":" + age + ",content:" + content + ", " + girlProperties;
    }

    @RequestMapping("/speak/{word}")
    public String speak(@PathVariable String word) {
        return word;
    }

    // http://localhost:8081/hello/params?name=你好
    @RequestMapping("/params")
    public String params(@RequestParam(required = false, defaultValue = "默认值") String name) {
        return name;
    }

    @GetMapping("get")
    public String get() {
        return "get";
    }
}
