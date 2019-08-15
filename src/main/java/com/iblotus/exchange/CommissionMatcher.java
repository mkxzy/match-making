package com.iblotus.exchange;


/**
 * 委托成交
 */
public interface CommissionMatcher {

    /**
     * 匹配成交
     * @param commission
     * @param dealHandler
     */
    void matchAndDeal(Commission commission, DealHandler dealHandler);
}
