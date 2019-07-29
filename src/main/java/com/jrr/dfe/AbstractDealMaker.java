package com.jrr.dfe;


import java.math.BigDecimal;

public abstract class AbstractDealMaker<T extends Commission> implements DealMaker<T> {

    protected CommissionRecorder<T> own;

    public AbstractDealMaker(CommissionRecorder<T> own) {
        this.own = own;
    }

    public abstract boolean canDeal(CommissionRecorder<T> opponentMission);

    @Override
    public final Deal<T> makeDeal(CommissionRecorder<T> opponentMission) {
        long dealAmount;
        if(own.getCurrentAmount() < opponentMission.getCurrentAmount()){
            dealAmount = own.getCurrentAmount();
        }else{
            dealAmount = opponentMission.getCurrentAmount();
        }

        BigDecimal dealPrice = opponentMission.getPrice();
        opponentMission.subCurrentAmount(dealAmount);
        own.subCurrentAmount(dealAmount);
        System.out.printf("成交数量%d\n", dealAmount);
        return this.createDeal(dealPrice, dealAmount, opponentMission);
    }

    protected abstract Deal<T> createDeal(BigDecimal dealPrice, long dealAmount, CommissionRecorder<T> opponentMission);
}
