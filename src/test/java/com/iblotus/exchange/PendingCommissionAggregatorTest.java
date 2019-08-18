package com.iblotus.exchange;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PendingCommissionAggregatorTest {

    @Test
    public void testAggregate(){
        SimpleCommission commission1 =
                new SimpleCommission("a", BigDecimal.valueOf(10), 1, Side.Long);
        SimpleCommission commission2 =
                new SimpleCommission("b",BigDecimal.valueOf(9), 1, Side.Short);
        SimpleCommission commission3 =
                new SimpleCommission("b",BigDecimal.valueOf(9), 1, Side.Short);

        List<PendingCommission> commissionList = new ArrayList<>();
        commissionList.add(new PendingCommission(commission1));
        commissionList.add(new PendingCommission(commission2));
        commissionList.add(new PendingCommission(commission3));

        DefaultPriceAggregator aggregator = new DefaultPriceAggregator();
        Map<BigDecimal, ?> map = aggregator.aggregate(commissionList);
        Assert.assertEquals(2, map.size());
    }
}
