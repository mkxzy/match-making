package com.iblotus.exchange;

import java.util.List;


/**
 * 挂单定位
 */
public interface CommissionBookIndexLocator {

    /**
     * 查找任务的待插入索引
     * 如果没有合适的，返回-1
     * @param commission
     * @return
     */
    int findProperIndex(PendingCommission commission, List<PendingCommission> list);
}
