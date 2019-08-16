package com.iblotus.exchange;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 挂单聚合器
 */
public class PendingCommissionAggregator implements Aggregator<PendingCommission, BigDecimal> {

    /**
     * 聚合
     * @param commissions
     * @return
     */
    @Override
    public Map<BigDecimal, List<PendingCommission>> aggregate(List<PendingCommission> commissions){
        // 必须保证顺序
        LinkedHashMap<BigDecimal, List<PendingCommission>> aggrMap = new LinkedHashMap<>();
        for (PendingCommission commission: commissions){
            if(aggrMap.containsKey(commission.getPrice())){
                List<PendingCommission> pendingCommissions = aggrMap.get(commission.getPrice());
                pendingCommissions.add(commission);
            }else {
                List<PendingCommission> pendingCommissions = new ArrayList<>();
                pendingCommissions.add(commission);
                aggrMap.put(commission.getPrice(), pendingCommissions);
            }
        }
        return aggrMap;
    }
}
