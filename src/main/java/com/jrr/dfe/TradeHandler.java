package com.jrr.dfe;


/**
 * 交易回调接口
 */
public interface TradeHandler<T extends Commission> {

    /**
     * 成交回调
     * @param deal
     */
    void onDeal(Deal<T> deal);

    /**
     * 委托更新
     */
    void commissionUpdated(CommissionBook<T> bidBook, CommissionBook<T> askBook);
}
