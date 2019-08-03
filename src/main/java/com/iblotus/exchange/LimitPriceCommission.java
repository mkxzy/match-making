package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 限价委托
 * @param
 */
public class LimitPriceCommission extends AbstractCommission {

    private final String id;

    private final BigDecimal price;

    private final LongShort direction;

    private long currentAmount;

    public LimitPriceCommission(String id, BigDecimal price, long amount,  LongShort direction){
        this.currentAmount = amount;
        this.price = price;
        this.direction = direction;
        this.id = id;
    }

    /**
     * 成交
     * @param own
     * @param opponent
     * @param dealHandler
     */
    @Override
    protected void deal(PendingBook<Commission> own,
                        PendingBook<Commission> opponent,
                        DealHandler dealHandler) {
        do {
            if(opponent.isEmpty()){
                own.add(this);
                break;
            }
            Commission passive = opponent.top();
            if(!this.canDeal(passive)){
                own.add(this);
                break;
            }
            Deal<Commission> deal = this.makeDeal(passive);
            if(dealHandler != null){
                dealHandler.onDeal(deal);
            }
            if(passive.getAmount() == 0){
                opponent.remove(passive);
            }
        } while (this.getAmount() > 0);
    }

    private boolean canDeal(Commission passive) {
        if(this.getDirection() == LongShort.Long){
            return this.getPrice().compareTo(passive.getPrice()) >= 0;
        }else{
            return this.getPrice().compareTo(passive.getPrice()) <= 0;
        }
    }

    private Deal<Commission> makeDeal(Commission passive) {
        long dealAmount;
        if(this.getAmount() < passive.getAmount()){
            dealAmount = this.getAmount();
        }else{
            dealAmount = passive.getAmount();
        }

        BigDecimal dealPrice = passive.getPrice();
        passive.substractAmount(dealAmount);
        this.substractAmount(dealAmount);
        SimpleCommissionDeal deal = new SimpleCommissionDeal(dealPrice, dealAmount, this, passive);
        return deal;
    }

    /**
     * 价格
     * @return
     */
    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 多空
     * @return
     */
    @Override
    public LongShort getDirection() {
        return this.direction;
    }

    /**
     * 获取当前数量
     * @return
     */
    public long getAmount() {
        return currentAmount;
    }

    /**
     * 当前数量减去相应数量（成交）
     * @param amount
     */
    public void substractAmount(long amount){
        currentAmount = currentAmount - amount;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
