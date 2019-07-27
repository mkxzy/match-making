package com.jrr.dfe;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CommissionMarketTest {

    @Test
    public void testBid(){
        SimpleCommission commission1 = new SimpleCommission(1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission(1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission(1, BigDecimal.valueOf(1.2));

        CommissionMarket market = new CommissionMarket();
        market.ask(commission1);
        market.ask(commission2);
        market.ask(commission3);

        SimpleCommission bidCommission = new SimpleCommission(2, BigDecimal.valueOf(1.1));
        market.bid(bidCommission);
        Assert.assertEquals(1, market.getBidBook().size());
        Assert.assertEquals(2, market.getAskBook().size());
    }

    @Test
    public void testBid2(){
        SimpleCommission commission1 = new SimpleCommission(1, BigDecimal.valueOf(1.1));
        SimpleCommission commission2 = new SimpleCommission(1, BigDecimal.valueOf(1.5));
        SimpleCommission commission3 = new SimpleCommission(1, BigDecimal.valueOf(1.2));

        CommissionMarket market = new CommissionMarket();
        market.ask(commission1);
        market.ask(commission2);
        market.ask(commission3);

        SimpleCommission bidCommission = new SimpleCommission(2, BigDecimal.valueOf(1.2));
        market.bid(bidCommission);
        Assert.assertEquals(0, market.getBidBook().size());
        Assert.assertEquals(1, market.getAskBook().size());
    }
}
