package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 成交实现类
 */
class SimpleCommissionDeal implements Deal<PendingCommission> {

    private final BigDecimal price;

    private final long amount;

    private final PendingCommission initiate;

    private final PendingCommission passive;

    SimpleCommissionDeal(BigDecimal price, long amount, PendingCommission initiate, PendingCommission passive) {
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
    public PendingCommission getInitiate() {
        return this.initiate;
    }

    @Override
    public PendingCommission getPassive() {
        return this.passive;
    }
}
