package com.iblotus.exchange;


import java.math.BigDecimal;
import java.util.UUID;


/**
 * 限价委托
 * @param
 */
public class LimitPriceCommissionBroker implements CommissionBroker {

    private final String brokerId;

    private long currentAmount;

    private long amount;

    private BigDecimal price;

    private LongShort direction;

    public LimitPriceCommissionBroker(long amount, BigDecimal price, LongShort direction){
        this.currentAmount = amount;
        this.brokerId = UUID.randomUUID().toString();
        this.amount = amount;
        this.price = price;
        this.direction = direction;
    }

    @Override
    public void deal(CommissionBook<CommissionBroker> own,
                     CommissionBook<CommissionBroker> opponent,
                     DealHandler dealHandler) {
        do {
            if(opponent.isEmpty()){
                own.add(this);
                break;
            }
            CommissionBroker passive = opponent.top();
            if(!this.canDeal(passive)){
                own.add(this);
                break;
            }
            Deal deal = this.makeDeal(passive);
            if(dealHandler != null){
                dealHandler.onDeal(deal);
            }
            if(passive.getCurrentAmount() == 0){
                opponent.remove(passive.getBrokerId());
            }
        } while (this.getCurrentAmount() > 0);
    }

    private boolean canDeal(CommissionBroker passive) {
        if(this.getDirection() == LongShort.Long){
            return this.getPrice().compareTo(passive.getPrice()) >= 0;
        }else{
            return this.getPrice().compareTo(passive.getPrice()) <= 0;
        }
    }

    private Deal makeDeal(CommissionBroker passive) {
        long dealAmount;
        if(this.getCurrentAmount() < passive.getCurrentAmount()){
            dealAmount = this.getCurrentAmount();
        }else{
            dealAmount = passive.getCurrentAmount();
        }

        BigDecimal dealPrice = passive.getPrice();
        passive.subCurrentAmount(dealAmount);
        this.subCurrentAmount(dealAmount);
        SimpleDeal deal = new SimpleDeal(dealPrice, dealAmount, this, passive);
        return deal;
    }

    @Override
    public long getAmount() {
        return this.amount;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public LongShort getDirection() {
        return this.direction;
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

    @Override
    public String getBrokerId() {
        return this.brokerId;
    }
}
