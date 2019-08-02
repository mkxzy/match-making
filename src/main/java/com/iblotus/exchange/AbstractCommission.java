package com.iblotus.exchange;


/**
 * 抽象委托
 */
public abstract class AbstractCommission implements Commission {

    @Override
    public void dealForLong(CommissionBook longBook, CommissionBook shortBook, CommissionDealHandler dealHandler) {
        this.deal(longBook, shortBook, dealHandler);
    }

    @Override
    public void dealForShort(CommissionBook longBook, CommissionBook shortBook, CommissionDealHandler dealHandler) {
        this.deal(shortBook, longBook, dealHandler);
    }

    protected abstract void deal(CommissionBook own, CommissionBook opponent, CommissionDealHandler dealHandler);
}
