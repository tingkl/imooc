package com.imooc.Exception;

import com.imooc.enums.ResultEnum;

/**
 * Created by tingkl on 2017/11/15.
 */
// RuntimeException才会导致事务回滚
public class GirlException extends RuntimeException {
    private Integer code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
