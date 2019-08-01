package com.iblotus.exchange;


import java.math.BigDecimal;

/**
 * 委托代理接口
 * 定义委托成交逻辑
 * @param <T>
 */
public interface CommissionBroker {

    /**
     * 代理Id
     * @return
     */
    String getBrokerId();

    /**
     * 买入成交
     * @param own
     * @param opponent
     * @param dealHandler
     */
    void deal(CommissionBook<CommissionBroker> own, CommissionBook<CommissionBroker> opponent, DealHandler dealHandler);

    /**
     * 获取当前数量
     * @return
     */
    long getCurrentAmount();

    /**
     * 当前数量减去一定数量
     */
    void subCurrentAmount(long amount);

    /**
     * 数量
     * @return
     */
    long getAmount();

    /**
     * 价格
     * @return
     */
    BigDecimal getPrice();

    /**
     * 行情方向
     */
    LongShort getDirection();
}
