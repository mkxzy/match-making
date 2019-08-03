package com.iblotus.exchange;


import java.math.BigDecimal;

/**
 * 委托代理接口
 * 定义委托成交逻辑
 */
public interface Commission {

    /**
     * 看多成交
     * @param longBook 买盘
     * @param shortBook 卖盘
     * @param dealHandler 成交回调
     */
    void dealForLong(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler);

    /**
     * 看空成交
     * @param longBook 买盘
     * @param shortBook 卖盘
     * @param dealHandler 成交回调
     */
    void dealForShort(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler);

    /**
     * 当前数量减去一定数量
     */
    void substractAmount(long amount);

    /**
     * 委托ID
     */
    String getId();

    /**
     * 获取当前数量
     */
    long getAmount();

    /**
     * 价格
     */
    BigDecimal getPrice();

    /**
     * 行情方向
     */
    LongShort getDirection();
}
