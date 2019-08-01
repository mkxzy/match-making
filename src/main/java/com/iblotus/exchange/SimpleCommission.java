package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 简单委托实现
 */
public class SimpleCommission implements Commission {

    private String id;

    private long amount;

    private BigDecimal price;

    public SimpleCommission(String id, long amount, BigDecimal price) {
        this.id = id;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
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
