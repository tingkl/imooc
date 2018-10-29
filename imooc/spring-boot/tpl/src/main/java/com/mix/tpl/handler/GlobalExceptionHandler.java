package com.mix.tpl.handler;

import com.mix.tpl.Exception.AreaNotExists;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AreaNotExists.class)
    @ResponseBody
    private Map<String, Object> areaNotExistsHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        System.out.println("areaNotExistsHandler");
        return modelMap;
    }
    // RuntimeException的父类是Exception
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        // 其他的异常走这里
//        e.printStackTrace();
        System.out.println("exceptionHandler");
        return modelMap;
    }
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    private Map<String, Object> AuthenticationExceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        // 其他的异常走这里
//        e.printStackTrace();
        System.out.println("AuthenticationExceptionHandler");
        return modelMap;
    }
}
