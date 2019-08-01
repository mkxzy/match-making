package com.iblotus.exchange;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.math.BigDecimal;

public class CommissionMatchingBenchmarkTest {

    private CommissionManager<SimpleCommission> market = new CommissionManager<>();

    @Test
    public void testBidBenchmark(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int count = 1000000+1;
        for(int i = 0; i < count; i++){
            SimpleCommission commission = new SimpleCommission(String.valueOf(i),1, BigDecimal.ONE);
            LimitPriceCommissionBroker<SimpleCommission> broker = new LimitPriceCommissionBroker<>(commission);
            if(i % 2 == 0){
                market.bid(broker);
            }else {
                market.ask(broker);
            }
        }
        stopWatch.stop();
        System.out.println(market.getBidBook().size());
        System.out.println(market.getAskBook().size());
        System.out.println(stopWatch.getTime());
        System.out.println("TPS: " + (count * 1000 / stopWatch.getTime()));
    }
}
