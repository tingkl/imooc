package com.mix.tpl.Exception;

public class AreaNotExists extends RuntimeException {
    public AreaNotExists() {
        super("不存在满足条件area");
    }
}
