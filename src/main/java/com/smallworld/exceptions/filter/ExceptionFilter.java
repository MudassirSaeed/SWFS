package com.smallworld.exceptions.filter;

import com.smallworld.exceptions.runtime.SmallWorldRuntimeException;
import com.smallworld.model.ErrorResponse;
import com.smallworld.util.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionFilter {

    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> exception(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCodeEnum.GENERAL_ERROR.getCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= UnsupportedOperationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> exception(UnsupportedOperationException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCodeEnum.OPERATION_NOT_SUPPORTED.getCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= SmallWorldRuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> exception(SmallWorldRuntimeException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getErrorCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
