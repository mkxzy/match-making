package com.iblotus.exchange.exceptions;


/**
 * 委托不存在异常
 */
public class CommissionNotExistException extends RuntimeException {

    public CommissionNotExistException(){
        super();
    }

    public CommissionNotExistException(String msg){
        super(msg);
    }
}
