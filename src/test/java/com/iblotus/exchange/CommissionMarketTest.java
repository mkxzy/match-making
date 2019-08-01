package com.iblotus.exchange;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CommissionMarketTest {

    @Test
    public void testBid(){
        SimpleCommission commission1 = new SimpleCommission("1",1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission("2",1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission("3",1, BigDecimal.valueOf(1.2));

        CommissionManager<SimpleCommission> market = new CommissionManager<>();
        market.ask(new LimitPriceCommissionBroker<>(commission1));
        market.ask(new LimitPriceCommissionBroker<>(commission2));
        market.ask(new LimitPriceCommissionBroker<>(commission3));

        SimpleCommission bidCommission = new SimpleCommission("4",2, BigDecimal.valueOf(1.1));
        market.bid(new LimitPriceCommissionBroker<>(bidCommission));
        Assert.assertEquals(1, market.getBidBook().size());
        Assert.assertEquals(2, market.getAskBook().size());
    }

    @Test
    public void testBid2(){
        SimpleCommission commission1 = new SimpleCommission("1",1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission("2",1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission("3",1, BigDecimal.valueOf(1.2));

        CommissionManager<SimpleCommission> market = new CommissionManager<>();
        market.ask(new LimitPriceCommissionBroker<>(commission1));
        market.ask(new LimitPriceCommissionBroker<>(commission2));
        market.ask(new LimitPriceCommissionBroker<>(commission3));

        SimpleCommission bidCommission = new SimpleCommission("4",2, BigDecimal.valueOf(1.2));
        market.bid(new LimitPriceCommissionBroker<>(bidCommission));
        Assert.assertEquals(0, market.getBidBook().size());
        Assert.assertEquals(1, market.getAskBook().size());
    }

    @Test
    public void testAsk(){
        SimpleCommission commission1 = new SimpleCommission("1",1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission("2",1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission("3",1, BigDecimal.valueOf(1.2));

        CommissionManager<SimpleCommission> market = new CommissionManager<>();
        market.bid(new LimitPriceCommissionBroker<>(commission1));
        market.bid(new LimitPriceCommissionBroker<>(commission2));
        market.bid(new LimitPriceCommissionBroker<>(commission3));

        SimpleCommission bidCommission = new SimpleCommission("4",2, BigDecimal.valueOf(1.0));
        market.ask(new LimitPriceCommissionBroker<>(bidCommission));
        Assert.assertEquals(1, market.getBidBook().size());
        Assert.assertEquals(0, market.getAskBook().size());
    }
}
