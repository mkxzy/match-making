package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;
import com.iblotus.exchange.exceptions.DuplicateCommissionException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


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
            TradeStrategy tradeStrategy = strategyResolver.resolve(commission.getStrategy());
            tradeStrategy.matchAndDeal(commission, handicap, dealHandler);
        }
    }

    /**
     * 撤单
     * @param id
     */
    public void putOff(String id){
        synchronized (locker){
            if(!this.handicap.contains(id)){
                throw new CommissionNotExistException();
            }
            handicap.removeById(id);
        }
    }

    /**
     * 买盘
     * @return
     */
    public List<PendingCommission> getLong() {
        return handicap.getLong();
    }

    /**
     * 卖盘
     * @return
     */
    public List<PendingCommission> getShort() {
        return handicap.getShort();
    }

    public Map<BigDecimal, List<PendingCommission>> getLongPriceAggr(Aggregator<PendingCommission, BigDecimal> aggregator){
        List<PendingCommission> commissions = this.getLong();
        return aggregator.aggregate(commissions);
    }

    public Map<BigDecimal, List<PendingCommission>> getShortPriceAggr(Aggregator<PendingCommission, BigDecimal> aggregator){
        List<PendingCommission> commissions = this.getShort();
        return aggregator.aggregate(commissions);
    }
}
