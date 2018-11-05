package com.mix.miaosha.exception;

import com.mix.miaosha.domain.result.CodeMsg;

public class GlobalException extends RuntimeException {
    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    private CodeMsg codeMsg;
    public GlobalException(CodeMsg codeMsg) {
        super();
        this.codeMsg = codeMsg;
    }
}
