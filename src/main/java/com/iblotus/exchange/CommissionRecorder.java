package com.iblotus.exchange;

import java.math.BigDecimal;


/**
 * 记录单个委托成交情况
 */
public class CommissionRecorder<T extends Commission> implements Commission {

    private final T commission;

    private long currentAmount;

    CommissionRecorder(T commission) {
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

    /**
     * 获取当前数量
     * @return
     */
    public long getCurrentAmount() {
        return currentAmount;
    }

    /**
     * 当前数量减去相应数量（成交）
     * @param amount
     */
    public void subCurrentAmount(long amount){
        currentAmount = currentAmount - amount;
    }

    /**
     * 获取委托
     * @return
     */
    public T getCommission() {
        return commission;
    }
}
