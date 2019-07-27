package com.jrr.dfe;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 委托成交记录器
 */
class CommissionRecorder<T extends Commission> implements Commission {

    private final T commission;

    private long currentAmount;

    public CommissionRecorder(T commission) {
        this.commission = commission;
        this.currentAmount = commission.getAmount();
    }

    public CommissionRecorder(T commission, long amount) {
        this.commission = commission;
        this.currentAmount = amount;
    }

    @Override
    public long getAmount() {
        return commission.getAmount();
    }

    @Override
    public BigDecimal getPrice() {
        return commission.getPrice();
    }

    public long getCurrentAmount() {
        return currentAmount;
    }

    public void subCurrentAmount(long amount){
        currentAmount = currentAmount - amount;
    }

    public T getCommission() {
        return commission;
    }
}
