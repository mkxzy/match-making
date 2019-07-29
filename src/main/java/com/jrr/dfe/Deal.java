package com.jrr.dfe;


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
     * 买方
     * @return
     */
    CommissionRecorder<T> getBid();

    /**
     * 卖方
     * @return
     */
    CommissionRecorder<T> getAsk();
}
