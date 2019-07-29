package com.jrr.dfe;


/**
 * 委托盘口
 */
public class CommissionMatching<T extends Commission> {

    // 买盘
    private final CommissionBook<T> bidBook = new CommissionBook<>(CommissionSortMode.HighPriceFirst);

    // 卖盘
    private final CommissionBook<T> askBook = new CommissionBook<>(CommissionSortMode.LowPriceFirst);

    // 成交处理
    private TradeHandler<T> tradeHandler;

    public CommissionMatching(){
    }

    public CommissionMatching(TradeHandler<T> handler){
        this.tradeHandler = handler;
    }

    /**
     * 买
     * @param commission
     */
    public void bid(T commission){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        trade(commissionRecorder, bidBook, askBook, new BidDealMaker<>(commissionRecorder));
    }

    /**
     * 卖
     * @param commission
     */
    public void ask(T commission){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        trade(commissionRecorder, askBook, bidBook, new AskDealMaker<>(commissionRecorder));
    }

    private void trade(CommissionRecorder<T> commissionRecorder,
                       CommissionBook<T> own,
                       CommissionBook<T> opponent,
                       DealMaker<T> dealCondition) {
        do {
            if(opponent.isEmpty()){
                own.add(commissionRecorder);
                break;
            }
            CommissionRecorder<T> top1 = opponent.head();
            if(!dealCondition.canDeal(top1)){
                own.add(commissionRecorder);
                break;
            }
            Deal<T> deal = dealCondition.makeDeal(top1);
            if(tradeHandler != null){
                tradeHandler.onDeal(deal);
            }
            if(top1.getCurrentAmount() == 0){
                opponent.remove(top1);
            }
        } while (commissionRecorder.getCurrentAmount() > 0);
        if(tradeHandler != null){
            tradeHandler.commissionUpdated(this.bidBook, this.askBook);
        }
    }

    public CommissionBook<T> getBidBook() {
        return bidBook;
    }

    public CommissionBook<T> getAskBook() {
        return askBook;
    }
}
