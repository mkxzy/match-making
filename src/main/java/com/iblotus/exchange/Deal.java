package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 成交抽象类
 */
public interface Deal<T> {

    /**
     * 成交价
     */
    BigDecimal getPrice();

    /**
     * 成交数量
     */
    long getAmount();

    /**
     * 主动方
     */
    T getInitiate();

    /**
     * 被动方
     */
    T getPassive();
}
