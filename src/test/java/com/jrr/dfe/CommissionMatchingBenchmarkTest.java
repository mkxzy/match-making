package com.jrr.dfe;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.math.BigDecimal;

public class CommissionMatchingBenchmarkTest {

    CommissionMatching<SimpleCommission> market = new CommissionMatching<>();

    @Test
    public void testBidBenchmark(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int count = 1000000+1;
        for(int i = 0; i < count; i++){
            SimpleCommission commission = new SimpleCommission(String.valueOf(i),1, BigDecimal.ONE);
            if(i % 2 == 0){
                market.bid(commission);
            }else {
                market.ask(commission);
            }
        }
        stopWatch.stop();
        System.out.println(market.getBidBook().size());
        System.out.println(market.getAskBook().size());
        System.out.println(stopWatch.getTime());
        System.out.println("TPS: " + (count * 1000 / stopWatch.getTime()));
    }
}
