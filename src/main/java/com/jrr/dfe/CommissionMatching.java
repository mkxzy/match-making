package com.jrr.dfe;


/**
 * 委托盘口
 */
public class CommissionMatching<T extends Commission> {

    // 买盘
    private final CommissionBook<T> bidBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook<T> askBook = CommissionBook.LowFirst();

    private DealHandler<T> dealHandler;

    private CommissionDealMaker<T> dealMaker = new LimitPriceCommissionDealMaker<>();

    public CommissionMatching(){
    }

    public CommissionMatching(DealHandler<T> dealHandler){
        this.dealHandler = dealHandler;
    }

    /**
     * 买
     * @param commission
     */
    public void bid(T commission){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        this.getDefaultDealMaker().bid(commissionRecorder, bidBook, askBook, dealHandler);
    }

    public void bid(T commission, CommissionDealMaker<T> dealMaker){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        dealMaker.bid(commissionRecorder, bidBook, askBook, dealHandler);
    }

    /**
     * 卖
     * @param commission
     */
    public void ask(T commission){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        this.getDefaultDealMaker().ask(commissionRecorder, bidBook, askBook, dealHandler);
    }

    public void ask(T commission, CommissionDealMaker<T> dealMaker){
        final CommissionRecorder<T> commissionRecorder = new CommissionRecorder<>(commission);
        dealMaker.ask(commissionRecorder, bidBook, askBook, dealHandler);
    }

    private CommissionDealMaker<T> getDefaultDealMaker(){
        return dealMaker;
    }

    public CommissionBook<T> getBidBook() {
        return bidBook;
    }

    public CommissionBook<T> getAskBook() {
        return askBook;
    }
}
