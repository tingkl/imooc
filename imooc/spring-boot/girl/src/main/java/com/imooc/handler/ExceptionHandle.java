package com.imooc.handler;

import com.imooc.Exception.GirlException;
import com.imooc.domain.Result;
import com.imooc.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by tingkl on 2017/11/15.
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof GirlException) {
            return ResultUtil.error(((GirlException) e).getCode(), e.getMessage());
        } else {
            logger.error("[系统异常]{}", e);
            return ResultUtil.error(-1, e.getMessage());
        }
    }
}
