package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 成交抽象类
 */
public interface Deal {

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
     * 买方
     * @return
     */
    CommissionBroker getInitiate();

    /**
     * 卖方
     * @return
     */
    CommissionBroker getPassive();
}
