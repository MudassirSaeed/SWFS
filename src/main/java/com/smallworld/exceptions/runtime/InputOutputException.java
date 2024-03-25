package com.smallworld.exceptions.runtime;

import com.smallworld.util.ErrorCodeEnum;

public class InputOutputException extends SmallWorldRuntimeException{
    private final int errorCode;
    public InputOutputException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getMessage());
        this.errorCode = errorCodeEnum.getCode();
    }
    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }
}
