package com.iblotus.exchange;


/**
 * 挂单监听
 */
public interface CommissionBookListener {

    /**
     * 新增委托
     * @param sender
     * @param commission
     */
    void onAdd(CommissionBook sender, Commission commission);

    /**
     * 删除委托
     * @param sender
     * @param commission
     */
    void onRemove(CommissionBook sender, Commission commission);
}
