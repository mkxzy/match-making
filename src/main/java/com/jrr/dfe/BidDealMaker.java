package com.jrr.dfe;

import java.math.BigDecimal;


/**
 * 买成交
 * @param <T>
 */
class BidDealMaker<T extends Commission> extends AbstractDealMaker<T> {

    public BidDealMaker(CommissionRecorder<T> own) {
        super(own);
    }

    @Override
    public boolean canDeal(CommissionRecorder<T> opponentMission) {
        return own.getPrice().compareTo(opponentMission.getPrice()) >= 0;
    }

    @Override
    protected Deal<T> createDeal(BigDecimal dealPrice, long dealAmount, CommissionRecorder<T> opponentMission) {
        SimpleDeal<T> deal = new SimpleDeal<>(dealPrice, dealAmount, own, opponentMission);
        return deal;
    }
}
