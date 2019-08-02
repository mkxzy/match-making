package com.iblotus.exchange;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * 功能测试
 */
public class CommissionManagerTest {

    @Test
    public void testSubmitWithDeal(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",1, BigDecimal.valueOf(10), LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",1, BigDecimal.valueOf(9), LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
        Assert.assertEquals(0, manager.getLongBook().size());
        Assert.assertEquals(0, manager.getShortBook().size());
    }

    @Test
    public void testSubmitWithoutDeal(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",1, BigDecimal.valueOf(10), LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",1, BigDecimal.valueOf(11), LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
        Assert.assertEquals(1, manager.getLongBook().size());
        Assert.assertEquals(1, manager.getShortBook().size());
    }

    @Test
    public void testCancel(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",1, BigDecimal.valueOf(10), LongShort.Long);
        manager.submit(commission1);
        manager.cancel("a");
        Assert.assertEquals(0, manager.getLongBook().size());
    }
}
