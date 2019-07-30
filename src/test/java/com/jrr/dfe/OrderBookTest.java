package com.jrr.dfe;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderBookTest {

    @Test
    public void testAddLowFirst(){
        CommissionBook<SimpleCommission> orderBook = CommissionBook.LowFirst();
        SimpleCommission commission1 = new SimpleCommission(1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission(1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission(1, BigDecimal.valueOf(1.2));
        orderBook.add(new CommissionRecorder<>(commission1));
        orderBook.add(new CommissionRecorder<>(commission2));
        orderBook.add(new CommissionRecorder<>(commission3));
        int index = 2;
        BigDecimal expected = BigDecimal.valueOf(1.5);
        BigDecimal result = orderBook.get(index).getPrice();
        System.out.println(result);
        Assert.assertTrue(expected.equals(result));
    }

    @Test
    public void testAddHighFirst(){
        CommissionBook<SimpleCommission> orderBook = CommissionBook.HighFirst();
        SimpleCommission commission1 = new SimpleCommission(1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission(1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission(1, BigDecimal.valueOf(1.2));
        orderBook.add(new CommissionRecorder<>(commission1));
        orderBook.add(new CommissionRecorder<>(commission2));
        orderBook.add(new CommissionRecorder<>(commission3));
        int index = 2;
        BigDecimal expected = BigDecimal.valueOf(1.1);
        BigDecimal result = orderBook.get(index).getPrice();
        System.out.println(result);
        Assert.assertTrue(expected.equals(result));
    }
}
