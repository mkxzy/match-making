package com.iblotus.exchange;


import java.math.BigDecimal;

/**
 * 委托代理接口
 * 定义委托成交逻辑
 */
public interface Commission {

    /**
     * 看多成交
     * @param longBook
     * @param shortBook
     * @param dealHandler
     */
    void dealForLong(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler);

    /**
     * 看空成交
     * @param longBook
     * @param
     * @param dealHandler
     */
    void dealForShort(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler);

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
