package com.smallworld.exceptions.runtime;

import com.smallworld.util.ErrorCodeEnum;

public class DataNotFoundException extends SmallWorldRuntimeException{
    private final int errorCode;
    public DataNotFoundException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getMessage());
        this.errorCode = errorCodeEnum.getCode();
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }
}
