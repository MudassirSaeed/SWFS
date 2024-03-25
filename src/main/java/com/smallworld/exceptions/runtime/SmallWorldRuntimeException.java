package com.smallworld.exceptions.runtime;

import com.smallworld.exceptions.SmallWorldException;

public abstract class SmallWorldRuntimeException extends RuntimeException implements SmallWorldException {


    public SmallWorldRuntimeException(){

    }
    public SmallWorldRuntimeException(String message){
        super(message);
    }

    public SmallWorldRuntimeException(Exception e){
        super(e);
    }

    public SmallWorldRuntimeException(String message, Exception e){
        super(message, e);
    }

}
