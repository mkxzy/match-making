package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 成交实现类
 * @param <T>
 */
public class SimpleDeal<T extends Commission> implements Deal<T> {

    private BigDecimal price;

    private long amount;

    private CommissionRecorder<T> bid;

    private CommissionRecorder<T> ask;

    public SimpleDeal(BigDecimal price, long amount, CommissionRecorder<T> bid, CommissionRecorder<T> ask) {
        this.price = price;
        this.amount = amount;
        this.bid = bid;
        this.ask = ask;
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
    public CommissionRecorder<T> getBid() {
        return null;
    }

    @Override
    public CommissionRecorder<T> getAsk() {
        return null;
    }
}
