package com.mix.tpl.web;

import com.mix.tpl.entity.Girl;
import com.mix.tpl.entity.Result;
import com.mix.tpl.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by tingkl on 2017/11/15.
 */
@RestController
@RequestMapping("/girls")
public class GirlController {
    private final static Logger logger = LoggerFactory.getLogger(GirlController.class);

    /**
     * 通过年龄查询所有女生列表
     *
     * @return
     */
    @GetMapping("/age/{age}")
    public Result girlListByAge(@PathVariable Integer age) {
        return ResultUtil.success(age);
    }

    @PostMapping("")
    // 如果不写@RequestBody,则前端必须用form-data提交数据
    public Result<Girl> girlAdd(@RequestBody @Valid Girl girl, BindingResult bindingResult) {
        logger.debug(girl.toString());
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(girl);
    }

    @PutMapping("/{id}")
    public Result girlUpdate(@PathVariable Integer id, String cupSize, Integer age) {
        return ResultUtil.success(id);
    }

    @DeleteMapping("/{id}")
    public Result girlDelete(@PathVariable("id") Integer id) {
        return ResultUtil.success(id);
    }
}
