package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 成交实现类
 */
class SimpleCommissionDeal implements Deal<Commission> {

    private final BigDecimal price;

    private final long amount;

    private final Commission initiate;

    private final Commission passive;

    SimpleCommissionDeal(BigDecimal price, long amount, Commission initiate, Commission passive) {
        this.price = price;
        this.amount = amount;
        this.initiate = initiate;
        this.passive = passive;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public long getAmount() {
        return this.amount;
    }

    @Override
    public Commission getInitiate() {
        return this.initiate;
    }

    @Override
    public Commission getPassive() {
        return this.passive;
    }
}
