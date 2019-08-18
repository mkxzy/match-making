package com.iblotus.exchange;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 挂单聚合器
 */
public class DefaultPriceAggregator implements PriceAggregator {

    private int scale = 1;

    private RoundingMode mode = RoundingMode.HALF_UP;

    public DefaultPriceAggregator(){

    }

    public DefaultPriceAggregator(int scale, RoundingMode roundingMode){
        this.scale = scale;
        this.mode = roundingMode;
    }

    /**
     * 聚合
     * @param commissions
     * @return
     */
    @Override
    public Map<BigDecimal, List<PendingCommission>> aggregate(List<PendingCommission> commissions){
        return this.aggregate(commissions, 0);
    }

    @Override
    public Map<BigDecimal, List<PendingCommission>> aggregate(List<PendingCommission> commissions, int limit) {
        int mapSize = 0;
        // 必须保证顺序
        LinkedHashMap<BigDecimal, List<PendingCommission>> aggrMap = new LinkedHashMap<>();
        for (PendingCommission commission: commissions){
            if(aggrMap.containsKey(commission.getPrice().setScale(scale, mode))){
                List<PendingCommission> pendingCommissions = aggrMap.get(commission.getPrice());
                pendingCommissions.add(commission);
            }else {
                List<PendingCommission> pendingCommissions = new ArrayList<>();
                pendingCommissions.add(commission);
                aggrMap.put(commission.getPrice(), pendingCommissions);
                if(limit > 0 && limit == ++mapSize){
                    break;
                }
            }
        }
        return aggrMap;
    }
}
