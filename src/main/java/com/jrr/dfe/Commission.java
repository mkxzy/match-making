package com.jrr.dfe;

import java.math.BigDecimal;


/**
 * 委托接口
 */
public interface Commission {

    long getAmount();

    BigDecimal getPrice();
}
