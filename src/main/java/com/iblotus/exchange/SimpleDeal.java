package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 成交实现类
 * @param <T>
 */
class SimpleDeal implements Deal {

    private BigDecimal price;

    private long amount;

    private Commission initiate;

    private Commission passive;

    SimpleDeal(BigDecimal price, long amount, Commission initiate, Commission passive) {
        this.price = price;
        this.amount = amount;
        this.initiate = initiate;
        this.passive = passive;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public Commission getInitiate() {
        return null;
    }

    @Override
    public Commission getPassive() {
        return null;
    }
}
