package com.mix.tpl.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by tingkl on 2017/11/15.
 */
public class Girl extends Base {
    private Integer id;
    private String cupSize;
    @Min(value = 18, message = "未成年少女禁止入门")
    private Integer age;

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @NotNull(message = "金额必填")
    private Float money;


    public Girl() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}

