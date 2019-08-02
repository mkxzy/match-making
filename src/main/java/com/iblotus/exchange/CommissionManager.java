package com.iblotus.exchange;


import java.util.HashMap;
import java.util.Map;


/**
 * 委托管理
 *
 * 处理委托，维护委托队列
 */
public class CommissionManager {

    private final Object locker = new Object();

    // 买盘
    private final CommissionBook longBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook shortBook = CommissionBook.LowFirst();

    // 委托归属
    private Map<String, CommissionBook> commissionBelong = new HashMap<>();

    // 成交处理
    private CommissionDealHandler dealHandler;

    public CommissionManager(){
        CommissionBookListener listener = new CommissionBookListener() {
            @Override
            public void onAdd(CommissionBook sender, Commission commission) {
                commissionBelong.put(commission.getId(), sender);
            }

            @Override
            public void onRemove(CommissionBook sender, Commission commission) {
                commissionBelong.remove(commission.getId());
            }
        };
        longBook.addListener(listener);
        shortBook.addListener(listener);
    }

    public CommissionManager(CommissionDealHandler dealHandler){
        this();
        this.dealHandler = dealHandler;
    }

    /**
     * 提交委托
     * @param commission
     */
    public void submit(Commission commission){
        if(commissionBelong.containsKey(commission.getId())){
            throw new RuntimeException("委托已存在");
        }
        synchronized(locker) {
            if(commission.getDirection() == LongShort.Long){
                commission.dealForLong(longBook, shortBook, dealHandler);
            }else if(commission.getDirection() == LongShort.Short){
                commission.dealForShort(longBook, shortBook, dealHandler);
            }else {
                throw new RuntimeException("Direction Invalid");
            }
        }
    }

    /**
     * 撤销委托
     * @param id
     */
    public void cancel(String id){
        synchronized (locker){
            CommissionBook commissionBook = commissionBelong.get(id);
            if(commissionBook != null){
                commissionBelong.remove(id);
                Commission commission = commissionBook.find(id);
                if(commission != null){
                    commissionBook.remove(commission);
                    return;
                }
            }
            throw new RuntimeException("撤单失败");
        }
    }

    /**
     * 买盘
     * @return
     */
    public CommissionBook getLongBook() {
        return longBook;
    }

    /**
     * 卖盘
     * @return
     */
    public CommissionBook getShortBook() {
        return shortBook;
    }
}
