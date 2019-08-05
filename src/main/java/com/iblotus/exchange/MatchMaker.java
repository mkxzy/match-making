package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;
import com.iblotus.exchange.exceptions.CommissionPropertyException;
import com.iblotus.exchange.exceptions.DuplicateCommissionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 委托管理
 *
 * 处理委托，维护委托队列
 */
public class MatchMaker {

    private final Object locker = new Object();

    // 买盘
    private final CommissionBook longBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook shortBook = CommissionBook.LowFirst();

    // 委托归属
    private Map<String, CommissionBook> commissionBelong = new HashMap<>();

    // 成交处理
    private DealHandler dealHandler;

    @Deprecated
    public MatchMaker(){
        this(null);
    }

    public MatchMaker(DealHandler dealHandler){
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
        this.dealHandler = dealHandler;
    }

    /**
     * 提交委托
     * @param commission
     */
    public void submit(Commission commission){
        if(commissionBelong.containsKey(commission.getId())){
            throw new DuplicateCommissionException();
    }
        synchronized(locker) {
            if(commission.getDirection() == LongShort.Long){
                commission.dealForLong(longBook, shortBook, dealHandler);
            }else if(commission.getDirection() == LongShort.Short){
                commission.dealForShort(longBook, shortBook, dealHandler);
            }else {
                throw new CommissionPropertyException("Direction must be Long or Short");
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
            throw new CommissionNotExistException();
        }
    }

    /**
     * 买盘
     * @return
     */
    public List<Commission> getLongs() {
        return longBook.toList();
    }

    /**
     * 卖盘
     * @return
     */
    public List<Commission> getShorts() {
        return shortBook.toList();
    }
}
