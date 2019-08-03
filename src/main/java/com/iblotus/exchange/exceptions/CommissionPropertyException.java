package com.iblotus.exchange.exceptions;


/**
 * 委托属性异常
 */
public class CommissionPropertyException extends RuntimeException {
    public CommissionPropertyException(){
        super();
    }

    public CommissionPropertyException(String msg){
        super(msg);
    }
}
