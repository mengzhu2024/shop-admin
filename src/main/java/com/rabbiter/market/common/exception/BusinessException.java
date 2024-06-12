package com.rabbiter.market.common.exception;

import com.rabbiter.market.common.constants.HttpStatus;

public class BusinessException extends SysException {
    public BusinessException(String message, Integer code) {
        super(message, code);
    }

    public BusinessException(String msg) {
        super(msg, HttpStatus.CODE_BUSINESS_ERROR);
    }
}
