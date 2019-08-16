package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 交易策略模板
 */
public abstract class TradeStrategyTemplate implements TradeStrategy {

    /**
     * 匹配成交
     * @param commission
     * @param handicap
     * @param dealHandler
     */
    @Override
    public void matchAndDeal(Commission commission,
                             CommissionHandicap handicap,
                             DealHandler dealHandler) {
        PendingCommission pendingItem = new PendingCommission(commission);
        do {
            if(handicap.isOpponentEmpty(pendingItem)){
                handicap.add(pendingItem);
                break;
            }
            PendingCommission passive = handicap.opponentTop(pendingItem);
            if(!this.canMatch(pendingItem, passive)){
                handicap.add(pendingItem);
                break;
            }
            Deal<PendingCommission> deal = this.makeDeal(pendingItem, passive);
            if(dealHandler != null){
                dealHandler.onDeal(deal);
            }
            if(passive.isFullDeal()){
                handicap.remove(passive);
            }
        } while (!pendingItem.isFullDeal());
    }

    protected abstract boolean canMatch(PendingCommission commission, PendingCommission passive);

    protected Deal<PendingCommission> makeDeal(PendingCommission commission, PendingCommission passive) {
        long dealAmount = commission.dealAmountWith(passive);
        BigDecimal dealPrice = passive.getPrice();
        SimpleCommissionDeal deal = new SimpleCommissionDeal(dealPrice, dealAmount, commission, passive);
        return deal;
    }
}
