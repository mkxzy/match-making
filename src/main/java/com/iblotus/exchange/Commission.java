package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 委托抽象类
 */
public interface Commission {

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
}
