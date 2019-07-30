package com.jrr.dfe;


/**
 * 委托成交
 * @param <T>
 */
public interface CommissionDealMaker<T extends Commission> {

    /**
     * 买
     * @param cr
     * @param bidBook
     * @param askBook
     */
    void bid(CommissionRecorder<T> cr, CommissionBook<T> bidBook, CommissionBook<T> askBook, DealHandler<T> dealHandler);

    /**
     * 卖
     * @param cr
     * @param bidBook
     * @param askBook
     */
    void ask(CommissionRecorder<T> cr, CommissionBook<T> bidBook, CommissionBook<T> askBook, DealHandler<T> dealHandler);
}
