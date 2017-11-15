package com.imooc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tingkl on 2017/11/15.
 */
@Aspect
@Component
public class HttpAspect {
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    @Pointcut("execution(public * com.imooc.controller.GirlController.*(..))")
    public void log() {

    }
    // @Before("execution(public * com.imooc.controller.GirlController.girlList(..))")
    // @Before("execution(public * com.imooc.controller.GirlController.*(..))")
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("before log");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // url
        logger.info("url={}", request.getRequestURL());
        // method
        logger.info("method={}", request.getMethod());
        // ip
        logger.info("ip={}", request.getRemoteAddr());
        // 方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + '.' + joinPoint.getSignature().getName());
        // 参数
        logger.info("args={}", joinPoint.getArgs());
    }

    // @After("execution(public * com.imooc.controller.GirlController.*(..))")
    @After("log()")
    public void doAfter() {
        logger.info("do log");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", object.toString());
    }
}
