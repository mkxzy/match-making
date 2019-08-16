package com.iblotus.exchange;


/**
 * 限价委托
 * @param
 */
public class LimitPriceStrategy extends TradeStrategyTemplate {

    @Override
    protected boolean canMatch(PendingCommission commission, PendingCommission passive) {
        if(commission.getDirection() == Side.Long){
            return commission.getPrice().compareTo(passive.getPrice()) >= 0;
        }else{
            return commission.getPrice().compareTo(passive.getPrice()) <= 0;
        }
    }
}
