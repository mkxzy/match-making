package com.iblotus.exchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 价格聚合器
 */
public interface PriceAggregator {

    /**
     * 按价格聚合
     * @param commissions
     * @return
     */
    Map<BigDecimal, List<PendingCommission>> aggregate(List<PendingCommission> commissions);

    /**
     * 按价格聚合并返回前N个
     * @param commissions
     * @param limit
     * @return
     */
    Map<BigDecimal, List<PendingCommission>> aggregate(List<PendingCommission> commissions, int limit);
}
