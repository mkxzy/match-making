package com.iblotus.exchange;


/**
 * 委托成交回调接口
 */
public interface DealHandler {

    /**
     * 回调
     * @param deal
     */
    void onDeal(Deal<Commission> deal);
}
