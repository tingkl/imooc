package com.mix.miaosha.exception;

import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    // validator异常
    @ExceptionHandler(value=BindException.class)
    public Result exceptionHandler(HttpServletRequest request, BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        ObjectError error = errors.get(0);
        String msg = error.getDefaultMessage();
        CodeMsg codeMsg = CodeMsg.BIND_ERROR.fillArgs(msg);
        System.out.println("validator异常:" + codeMsg);
        return Result.error(codeMsg);
    }

    // 自定义的全局异常
    @ExceptionHandler(value=GlobalException.class)
    public Result exceptionHandler(HttpServletRequest request, GlobalException e) {
        System.out.println("自定义全局异常:" + e.getCodeMsg());
        return Result.error(e.getCodeMsg());
    }

    @ExceptionHandler(value=Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
