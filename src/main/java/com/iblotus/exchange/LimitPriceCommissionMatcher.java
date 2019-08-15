package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionPropertyException;

import java.math.BigDecimal;


/**
 * 限价委托
 * @param
 */
public class LimitPriceCommissionMatcher extends AbstractCommissionMatcher {

    public LimitPriceCommissionMatcher(PendingBook<PendingCommission> longBook, PendingBook<PendingCommission> shortBook) {
        super(longBook, shortBook);
    }

    @Override
    public void matchAndDeal(Commission commission, DealHandler dealHandler) {
        PendingCommission pendingItem = new PendingCommission(commission);
        if(commission.getDirection() == Side.Long){
            this.deal(pendingItem, longBook, shortBook, dealHandler);
        }else if(commission.getDirection() == Side.Short){
            this.deal(pendingItem, shortBook, longBook, dealHandler);
        }else {
            throw new CommissionPropertyException("Direction must be Long or Short");
        }
    }

    protected void deal(PendingCommission commission,
                        PendingBook<PendingCommission> own,
                        PendingBook<PendingCommission> opponent,
                        DealHandler dealHandler) {
        do {
            if(opponent.isEmpty()){
                own.add(commission);
                break;
            }
            PendingCommission passive = opponent.top();
            if(!this.canDeal(commission, passive)){
                own.add(commission);
                break;
            }
            Deal<PendingCommission> deal = this.makeDeal(commission, passive);
            if(dealHandler != null){
                dealHandler.onDeal(deal);
            }
            if(passive.isFullDeal()){
                opponent.remove(passive);
            }
        } while (!commission.isFullDeal());
    }

    protected boolean canDeal(PendingCommission commission, PendingCommission passive) {
        if(commission.getDirection() == Side.Long){
            return commission.getPrice().compareTo(passive.getPrice()) >= 0;
        }else{
            return commission.getPrice().compareTo(passive.getPrice()) <= 0;
        }
    }

    protected Deal<PendingCommission> makeDeal(PendingCommission commission, PendingCommission passive) {
        long dealAmount = commission.dealAmountWith(passive);
        BigDecimal dealPrice = passive.getPrice();
        SimpleCommissionDeal deal = new SimpleCommissionDeal(dealPrice, dealAmount, commission, passive);
        return deal;
    }
}
