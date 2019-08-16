package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.DuplicateCommissionException;

import java.util.List;


/**
 * 委托管理
 *
 * 处理委托，维护委托队列
 */
public class TradeMarket {

    private final Object locker = new Object();

    // 成交处理
    private DealHandler dealHandler;

    // 盘口
    private final CommissionHandicap handicap = new CommissionHandicap();

    private TradeStrategyResolver strategyResolver;

    public TradeMarket(){
        this.init(null, new DefaultTradeStrategyResolver());
    }

    public TradeMarket(DealHandler dealHandler){
        this.init(dealHandler, new DefaultTradeStrategyResolver());
    }

    public TradeMarket(DealHandler dealHandler, TradeStrategyResolver resolver){
        this.init(dealHandler, resolver);
    }

    /**
     * 初始化成员变量
     * @param dealHandler
     * @param resolver
     */
    private void init(DealHandler dealHandler, TradeStrategyResolver resolver){
        this.dealHandler = dealHandler;
        this.strategyResolver = resolver;
    }

    /**
     * 挂单
     * @param commission
     */
    public void putOn(Commission commission){
        synchronized (locker){
            if(this.handicap.contains(commission.getId())){
                throw new DuplicateCommissionException();
            }
            TradeStrategy commissionMatcher = new LimitPriceStrategy();
            commissionMatcher.matchAndDeal(commission, handicap, dealHandler);
        }
    }

    /**
     * 撤单
     * @param id
     */
    public void putOff(String id){
        synchronized (locker){
            handicap.removeById(id);
        }
    }

    /**
     * 买盘
     * @return
     */
    public List<PendingCommission> getLongs() {
        return handicap.getLong();
    }

    /**
     * 卖盘
     * @return
     */
    public List<PendingCommission> getShorts() {
        return handicap.getShort();
    }
}
