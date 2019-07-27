package com.jrr.dfe;


/**
 * 委托盘口
 */
public class CommissionMarket<T extends Commission> {

    private final CommissionBook bidBook = new CommissionBook(CommissionSortMode.HighPriceFirst);

    private final CommissionBook askBook = new CommissionBook(CommissionSortMode.LowPriceFirst);

    public void bid(T commission){
        long leftAmount = commission.getAmount();
        do {
            if(askBook.isEmpty()){
                bidBook.add(new CommissionRecorder<>(commission, leftAmount));
                break;
            }
            CommissionRecorder top1 = askBook.head();
            if(commission.getPrice().compareTo(top1.getPrice()) < 0){
                bidBook.add(new CommissionRecorder<>(commission, leftAmount));
                break;
            }
            if(leftAmount < top1.getCurrentAmount()){
                top1.subCurrentAmount(leftAmount);
                System.out.printf("成交数量%d\n", leftAmount);
                break;
            }else{
                askBook.remove(top1);
                leftAmount = leftAmount - top1.getCurrentAmount();
                System.out.printf("成交数量%d\n", top1.getCurrentAmount());
            }
        } while (leftAmount > 0);
    }

    public void ask(T commission){
        askBook.add(new CommissionRecorder<>(commission));
    }

    public CommissionBook getBidBook() {
        return bidBook;
    }

    public CommissionBook getAskBook() {
        return askBook;
    }
}
