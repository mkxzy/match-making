package com.jrr.dfe;

import java.math.BigDecimal;


/**
 * 简单委托实现
 */
public class SimpleCommission implements Commission {

    private long amount;

    private BigDecimal price;

    public SimpleCommission(long amount, BigDecimal price) {
        this.amount = amount;
        this.price = price;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
