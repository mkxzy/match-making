package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 定义委托接口
 */
public interface Commission {

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
    Side getDirection();

    /**
     * 委托策略
     * @return
     */
    String getStrategy();
}
