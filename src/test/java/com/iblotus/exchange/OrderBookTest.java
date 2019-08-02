package com.iblotus.exchange;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderBookTest {

    @Test
    public void testAddLowFirst(){
        CommissionBook<Commission> orderBook = CommissionBook.LowFirst();
        LimitPriceCommission commission1 = new LimitPriceCommission("1",1, BigDecimal.valueOf(1.1), LongShort.Long);
        LimitPriceCommission commission2 = new LimitPriceCommission("2",1, BigDecimal.valueOf(1.5), LongShort.Long);
        LimitPriceCommission commission3 = new LimitPriceCommission("3",1, BigDecimal.valueOf(1.2), LongShort.Long);
        LimitPriceCommission commission4 = new LimitPriceCommission("4",1, BigDecimal.valueOf(1.3), LongShort.Long);
        LimitPriceCommission commission5 = new LimitPriceCommission("5",1, BigDecimal.valueOf(1.3), LongShort.Long);
        orderBook.add(commission1);
        orderBook.add(commission2);
        orderBook.add(commission3);
        orderBook.add(commission4);
        orderBook.add(commission5);
        LimitPriceCommission[] ids = new LimitPriceCommission[]{
                commission1,
                commission3,
                commission4,
                commission5,
                commission2
        };
        for(int i = 0; i < 5; i++){
            String expected = ids[i].getId();
            String result = orderBook.get(i).getId();
            System.out.println(result);
            Assert.assertTrue(expected.equals(result));
        }
    }

//    @Test
//    public void testAddHighFirst(){
//        CommissionBook<SimpleCommission> orderBook = CommissionBook.HighFirst();
//        SimpleCommission commission1 = new SimpleCommission("1",1, BigDecimal.valueOf(1.1));
//        SimpleCommission commission2 = new SimpleCommission("2",1, BigDecimal.valueOf(1.5));
//        SimpleCommission commission3 = new SimpleCommission("3",1, BigDecimal.valueOf(1.2));
//        SimpleCommission commission4 = new SimpleCommission("4",1, BigDecimal.valueOf(1.3));
//        SimpleCommission commission5 = new SimpleCommission("5",1, BigDecimal.valueOf(1.1));
//        orderBook.add(new CommissionRecorder<>(commission1));
//        orderBook.add(new CommissionRecorder<>(commission2));
//        orderBook.add(new CommissionRecorder<>(commission3));
//        orderBook.add(new CommissionRecorder<>(commission4));
//        orderBook.add(new CommissionRecorder<>(commission5));
//        String[] ids = new String[]{"2", "4", "3", "1", "5"};
//        for(int i = 0; i < 5; i++){
//            String expected = ids[i];
//            String result = orderBook.get(i).getCommission().getId();
//            System.out.println(result);
//            Assert.assertTrue(expected.equals(result));
//        }
//    }
}
