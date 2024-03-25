package com.smallworld.util;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    OPERATION_NOT_SUPPORTED(1000,"Operation Not Supported"),
    DATA_NOT_FOUND(1001,"Data not found for your operation"),
    UNABLE_TO_FETCH_DATA(1002,"Unable to Fetch Data"),
    GENERAL_ERROR(9999,"Unexpected Error Occurred");
    private final int code;
    private final String message;
    ErrorCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

}

