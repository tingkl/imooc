package com.imooc.controller;

import com.imooc.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tingkl on 2017/11/15.
 */
@Controller
public class TplController {
    @Autowired
    GirlProperties girlProperties;
    @RequestMapping(value = "/tpl", method = RequestMethod.GET)
    public String say() {
        return "index";
    }

}
