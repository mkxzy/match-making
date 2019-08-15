package com.iblotus.exchange;


/**
 * 委托匹配器
 */
public abstract class AbstractCommissionMatcher implements CommissionMatcher {

    // 买盘
    protected final PendingBook<PendingCommission> longBook;

    // 卖盘
    protected final PendingBook<PendingCommission> shortBook;

    public AbstractCommissionMatcher(PendingBook<PendingCommission> longBook, PendingBook<PendingCommission> shortBook) {
        this.longBook = longBook;
        this.shortBook = shortBook;
    }
}
