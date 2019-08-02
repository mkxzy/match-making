package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 成交抽象类
 */
public interface Deal<T extends Commission> {

    /**
     * 成交价
     * @return
     */
    BigDecimal getPrice();

    /**
     * 成交数量
     * @return
     */
    long getAmount();

    /**
     * 主动方
     * @return
     */
    T getInitiate();

    /**
     * 被动方
     * @return
     */
    T getPassive();
}
