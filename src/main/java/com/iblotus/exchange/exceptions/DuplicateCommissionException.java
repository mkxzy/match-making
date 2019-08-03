package com.iblotus.exchange.exceptions;


/**
 * 重复委托异常
 */
public class DuplicateCommissionException extends RuntimeException {
    public DuplicateCommissionException(){
        super();
    }

    public DuplicateCommissionException(String msg){
        super(msg);
    }
}
