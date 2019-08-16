package com.iblotus.exchange;

import java.util.List;

/**
 * 委托定位策略
 */
public interface CommissionLocateStrategy {

    /**
     * 查找任务的待插入索引
     * 如果没有合适的，返回-1
     * @param newCommition
     * @return
     */
    int findProperIndex(PendingCommission newCommition, List<PendingCommission> list);
}
