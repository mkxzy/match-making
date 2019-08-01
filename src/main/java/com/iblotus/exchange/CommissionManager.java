package com.iblotus.exchange;


/**
 * 委托盘口
 *
 * 负责管理委托，委托排队
 */
public class CommissionManager<T extends Commission> {

    private final Object locker = new Object();

    // 买盘
    private final CommissionBook<T> bidBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook<T> askBook = CommissionBook.LowFirst();

    private DealHandler<T> dealHandler;

    public CommissionManager(){
    }

    public CommissionManager(DealHandler<T> dealHandler){
        this.dealHandler = dealHandler;
    }

    /**
     * 买入委托
     * @param commissionBroker
     */
    public void bid(CommissionBroker<T> commissionBroker){
        synchronized(locker) {
            commissionBroker.dealForBid(bidBook, askBook, dealHandler);
        }
    }

    /**
     * 卖出委托
     * @param commissionBroker
     */
    public void ask(CommissionBroker<T> commissionBroker){
        synchronized (locker){
            commissionBroker.dealForAsk(bidBook, askBook, dealHandler);
        }
    }

    public CommissionBook<T> getBidBook() {
        return bidBook;
    }

    public CommissionBook<T> getAskBook() {
        return askBook;
    }
}
