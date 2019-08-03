package com.iblotus.exchange;


/**
 * 抽象委托
 */
public abstract class AbstractCommission implements Commission {

    @Override
    public void dealForLong(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler) {
        this.deal(longBook, shortBook, dealHandler);
    }

    @Override
    public void dealForShort(PendingBook<Commission> longBook, PendingBook<Commission> shortBook, DealHandler dealHandler) {
        this.deal(shortBook, longBook, dealHandler);
    }

    protected abstract void deal(PendingBook<Commission> own, PendingBook<Commission> opponent, DealHandler dealHandler);
}
