package com.jrr.dfe;


/**
 * 委托模式
 * 负责处理委托成交的具体方式。确定成交价，成交数量，部分成交如何处理等等
 * @param <T>
 */
public interface CommissionBroker<T extends Commission> {

    /**
     * 买入成交
     * @param bidBook
     * @param askBook
     * @param dealHandler
     */
    void dealForBid(CommissionBook<T> bidBook, CommissionBook<T> askBook, DealHandler<T> dealHandler);

    /**
     * 卖出成交
     * @param bidBook
     * @param askBook
     * @param dealHandler
     */
    void dealForAsk(CommissionBook<T> bidBook, CommissionBook<T> askBook, DealHandler<T> dealHandler);
}
