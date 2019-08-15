package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;
import com.iblotus.exchange.exceptions.DuplicateCommissionException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


/**
 * 功能测试
 */
public class CommissionManagerTest {

    @Test
    public void testSubmitWithDeal(){
        MatchMaker manager = new MatchMaker();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(9), 1, Side.Short);
        manager.matchNow(commission1);
        manager.matchNow(commission2);
        Assert.assertEquals(0, manager.getLongs().size());
        Assert.assertEquals(0, manager.getShorts().size());
    }

    @Test
    public void testSubmitWithoutDeal(){
        MatchMaker manager = new MatchMaker();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(11), 1, Side.Short);
        manager.matchNow(commission1);
        manager.matchNow(commission2);
        Assert.assertEquals(1, manager.getLongs().size());
        Assert.assertEquals(1, manager.getShorts().size());
    }

    @Test(expected = DuplicateCommissionException.class)
    public void testSubmitWithDuplicateException(){
        MatchMaker manager = new MatchMaker();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("a",BigDecimal.valueOf(11), 1, Side.Short);
        manager.matchNow(commission1);
        manager.matchNow(commission2);
    }

    @Test
    public void testCancel(){
        MatchMaker manager = new MatchMaker();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        manager.matchNow(commission1);
        manager.cancel("a");
        Assert.assertEquals(0, manager.getLongs().size());
    }

    @Test(expected = CommissionNotExistException.class)
    public void testCancelFail(){
        MatchMaker manager = new MatchMaker();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        manager.matchNow(commission1);
        manager.cancel("b");
        Assert.assertEquals(0, manager.getLongs().size());
    }

    @Test
    public void testDealWithHandler(){
        DealHandler dealHandler = Mockito.mock(DealHandler.class);
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Deal<PendingCommission> deal = (Deal<PendingCommission>)invocationOnMock.getArguments()[0];
                System.out.printf("%f, %s, %s, %s\n",
                        deal.getPrice(),
                        deal.getAmount(),
                        deal.getInitiate().getId(),
                        deal.getPassive().getId());
                return null;
            }
        };
        doAnswer(answer).when(dealHandler).onDeal(any(Deal.class));
        MatchMaker manager = new MatchMaker(dealHandler);
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, Side.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(11), 1, Side.Long);
        LimitPriceCommission commission3 =
                new LimitPriceCommission("c",BigDecimal.valueOf(9), 2, Side.Short);
        manager.matchNow(commission1);
        manager.matchNow(commission2);
        manager.matchNow(commission3);
        verify(dealHandler, times( 2 )).onDeal(any(Deal.class));
    }
}
