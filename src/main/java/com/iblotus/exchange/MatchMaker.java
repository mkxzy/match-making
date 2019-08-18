package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;
import com.iblotus.exchange.exceptions.DuplicateCommissionException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 撮合
 */
public class MatchMaker {

    // 同步锁
    private final Object locker = new Object();

    // 盘口
    private final CommissionHandicap handicap = new CommissionHandicap();

    // 策略库
    private TradeStrategyResolver strategyResolver;

    // 成交处理
    private DealHandler dealHandler;

    public MatchMaker(){
        this.init(null, new DefaultTradeStrategyResolver());
    }

    public MatchMaker(DealHandler dealHandler){
        this.init(dealHandler, new DefaultTradeStrategyResolver());
    }

    public MatchMaker(DealHandler dealHandler, TradeStrategyResolver resolver){
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

    public Map<BigDecimal, List<PendingCommission>> getLongAggr(PriceAggregator aggregator, int depth){
        return aggregator.aggregate(this.getLong(), depth);
    }

    public Map<BigDecimal, List<PendingCommission>> getShortAggr(PriceAggregator aggregator, int depth){
        return aggregator.aggregate(this.getShort(), depth);
    }
}
