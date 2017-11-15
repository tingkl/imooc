package com.imooc.controller;

import com.imooc.domain.Girl;
import com.imooc.domain.Result;
import com.imooc.repository.GirlRepository;
import com.imooc.service.GirlService;
import com.imooc.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tingkl on 2017/11/15.
 */
@RestController
@RequestMapping("/girls")
public class GirlController {
    private final static Logger logger = LoggerFactory.getLogger(GirlController.class);

    @Autowired
    private GirlRepository girlRepository;

    /**
     * 查询所有女生列表
     * @return
     */
    @GetMapping("")
    public List<Girl> girlList() {
        logger.info("girlList");
        return girlRepository.findAll();
    }

    /**
     * 通过年龄查询所有女生列表
     * @return
     */
    @GetMapping("/age/{age}")
    public List<Girl> girlListByAge(@PathVariable Integer age) {
        return girlRepository.findByAge(age);
    }

    @PostMapping("")
    public Result<Girl> girlAdd(@Valid Girl girl, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(girlRepository.save(girl));
    }

    @GetMapping("/{id}")
    public Girl girlFindOne(@PathVariable Integer id) {
        return girlRepository.findOne(id);
    }

    @PutMapping("/{id}")
    public Girl girlUpdate(@PathVariable Integer id, String cupSize, Integer age) {
        Girl girl = new Girl();
        girl.setAge(age);
        girl.setCupSize(cupSize);
        girl.setId(id);
        return girlRepository.save(girl);
    }

    @DeleteMapping("/{id}")
    public void girlDelete(@PathVariable("id") Integer id) {
        girlRepository.delete(id);
    }

    @Autowired
    GirlService girlService;

    @PostMapping("/two")
    public void girlTwo() {
        girlService.insertTwo();
    }

    @GetMapping("/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception {
        girlService.getAge(id);
    }
}
