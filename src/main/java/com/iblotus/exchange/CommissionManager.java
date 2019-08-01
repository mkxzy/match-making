package com.iblotus.exchange;


/**
 * 委托盘口
 *
 * 处理委托，维护委托队列
 */
public class CommissionManager {

    private final Object locker = new Object();

    // 买盘
    private final CommissionBook<CommissionBroker> longBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook<CommissionBroker> shortBook = CommissionBook.LowFirst();

    private DealHandler dealHandler;

    public CommissionManager(){
    }

    public CommissionManager(DealHandler dealHandler){
        this.dealHandler = dealHandler;
    }

    /**
     * 提交委托
     * @param commissionBroker
     */
    public void submit(CommissionBroker commissionBroker){
        synchronized(locker) {
            if(commissionBroker.getDirection() == LongShort.Long){
                commissionBroker.deal(longBook, shortBook, dealHandler);
            }else if(commissionBroker.getDirection() == LongShort.Short){
                commissionBroker.deal(shortBook, longBook, dealHandler);
            }else {
                throw new RuntimeException("Unsupported Direction");
            }
        }
    }

    public void cancel(String brokerId){

    }

    public CommissionBook<CommissionBroker> getLongBook() {
        return longBook;
    }

    public CommissionBook<CommissionBroker> getShortBook() {
        return shortBook;
    }
}
