package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;
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

    private MatcherProvider provider;

    @Deprecated
    public MatchMaker(){
        this.init(null, new DefaultMatcherProvider(longBook, shortBook));
    }

    public MatchMaker(DealHandler dealHandler){
        this.init(dealHandler, new DefaultMatcherProvider(longBook, shortBook));
    }

    public MatchMaker(DealHandler dealHandler, MatcherProvider provider){
        this.init(dealHandler, provider);
    }

    private void init(DealHandler dealHandler, MatcherProvider provider){
        CommissionBookListener listener = new CommissionBookListener() {
            @Override
            public void onAdd(CommissionBook sender, PendingCommission commission) {
                commissionBelong.put(commission.getId(), sender);
            }

            @Override
            public void onRemove(CommissionBook sender, PendingCommission commission) {
                commissionBelong.remove(commission.getId());
            }
        };
        longBook.addListener(listener);
        shortBook.addListener(listener);
        this.dealHandler = dealHandler;
        this.provider = provider;
    }

    /**
     * 撮合
     * @param commission
     */
    public void matchNow(Commission commission){
        synchronized (locker){
            if(this.commissionBelong.containsKey(commission.getId())){
                throw new DuplicateCommissionException();
            }
            CommissionMatcher commissionMatcher = provider.findMatcher(commission.getMatcher());
            commissionMatcher.matchAndDeal(commission, dealHandler);
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
                PendingCommission commission = commissionBook.find(id);
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
    public List<PendingCommission> getLongs() {
        return longBook.toList();
    }

    /**
     * 卖盘
     * @return
     */
    public List<PendingCommission> getShorts() {
        return shortBook.toList();
    }
}
