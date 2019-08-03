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
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(9), 1, LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
        Assert.assertEquals(0, manager.getLongBook().size());
        Assert.assertEquals(0, manager.getShortBook().size());
    }

    @Test
    public void testSubmitWithoutDeal(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(11), 1, LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
        Assert.assertEquals(1, manager.getLongBook().size());
        Assert.assertEquals(1, manager.getShortBook().size());
    }

    @Test(expected = DuplicateCommissionException.class)
    public void testSubmitWithDuplicateException(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("a",BigDecimal.valueOf(11), 1, LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
    }

    @Test
    public void testCancel(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        manager.submit(commission1);
        manager.cancel("a");
        Assert.assertEquals(0, manager.getLongBook().size());
    }

    @Test(expected = CommissionNotExistException.class)
    public void testCancelFail(){
        CommissionManager manager = new CommissionManager();
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        manager.submit(commission1);
        manager.cancel("b");
        Assert.assertEquals(0, manager.getLongBook().size());
    }

    @Test
    public void testDealWithHandler(){
        DealHandler dealHandler = Mockito.mock(DealHandler.class);
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Deal<Commission> deal = (Deal<Commission>)invocationOnMock.getArguments()[0];
                System.out.printf("%f, %s, %s, %s\n",
                        deal.getPrice(),
                        deal.getAmount(),
                        deal.getInitiate().getId(),
                        deal.getPassive().getId());
                return null;
            }
        };
        doAnswer(answer).when(dealHandler).onDeal(any(Deal.class));
        CommissionManager manager = new CommissionManager(dealHandler);
        LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
        LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(11), 1, LongShort.Long);
        LimitPriceCommission commission3 =
                new LimitPriceCommission("c",BigDecimal.valueOf(9), 2, LongShort.Short);
        manager.submit(commission1);
        manager.submit(commission2);
        manager.submit(commission3);
        verify(dealHandler, times( 2 )).onDeal(any(Deal.class));
    }
}
