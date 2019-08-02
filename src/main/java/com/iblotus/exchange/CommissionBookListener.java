package com.iblotus.exchange;


/**
 * 挂单监听
 */
public interface CommissionBookListener<T extends Commission> {

    /**
     * 新增委托
     * @param sender
     * @param commission
     */
    void onAdd(CommissionBook<T> sender, T commission);

    /**
     * 删除委托
     * @param sender
     * @param commission
     */
    void onRemove(CommissionBook<T> sender, T commission);
}
