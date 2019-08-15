package com.iblotus.exchange;


/**
 * 挂单监听
 */
interface CommissionBookListener {

    /**
     * 新增委托
     * @param sender
     * @param commission
     */
    void onAdd(CommissionBook sender, PendingCommission commission);

    /**
     * 删除委托
     * @param sender
     * @param commission
     */
    void onRemove(CommissionBook sender, PendingCommission commission);
}
