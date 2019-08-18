package com.iblotus.exchange;


import java.math.BigDecimal;

/**
 * 挂单
 */
public class PendingCommission implements Commission {

    private Commission commission;

    private long dealAmount = 0;

    public PendingCommission(Commission commission){
        this.commission = commission;
    }

    @Override
    public String getId() {
        return this.commission.getId();
    }

    @Override
    public long getAmount() {
        return commission.getAmount();
    }

    @Override
    public BigDecimal getPrice() {
        return this.commission.getPrice();
    }

    @Override
    public Side getDirection() {
        return commission.getDirection();
    }

    @Override
    public String getStrategy() {
        return commission.getStrategy();
    }

    /**
     * 已成交数量
     * @return
     */
    public long getDealAmount(){
        return this.dealAmount;
    }

    /**
     * 是否完全成交
     * @return
     */
    public boolean isFullDeal(){
        return this.dealAmount == this.getAmount();
    }

    /**
     * 成交
     * @param passive
     * @return 成家数量
     */
    public long dealAmountWith(PendingCommission passive){
        long dealAmount = this.judgeDealAmount(passive);
        this.dealAmount += dealAmount;
        passive.dealAmount += dealAmount;
        return dealAmount;
    }

    /**
     * 确定成交数量
     * @param passive
     * @return
     */
    private long judgeDealAmount(PendingCommission passive){
        long dealAmount;
        if(this.getCurrentAmount() < passive.getCurrentAmount()){
            dealAmount = this.getCurrentAmount();
        }else{
            dealAmount = passive.getCurrentAmount();
        }
        return dealAmount;
    }

    /**
     * 获取剩余数量
     * @return
     */
    public long getCurrentAmount() {
        return this.getAmount() - this.getDealAmount();
    }
}
