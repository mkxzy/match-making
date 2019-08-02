package com.iblotus.exchange;


import java.math.BigDecimal;

/**
 * 委托代理接口
 * 定义委托成交逻辑
 */
public interface Commission {

    /**
     * 买入成交
     * @param own
     * @param opponent
     * @param dealHandler
     */
    void deal(CommissionBook own, CommissionBook opponent, DealHandler dealHandler);

    /**
     * 当前数量减去一定数量
     */
    void substractAmount(long amount);

    /**
     * 代理Id
     * @return
     */
    String getId();

    /**
     * 获取当前数量
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
