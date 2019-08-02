package com.iblotus.exchange;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * 性能测试
 */
public class CommissionMatchingBenchmarkTest {

    private CommissionManager market = new CommissionManager();

    @Ignore
    @Test
    public void testBidBenchmark(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long count = 10000000;
        for(int i = 0; i < count; i++){
            LongShort direction;
            if(i % 2 == 0){
                direction = LongShort.Long;
            }else {
                direction = LongShort.Short;
            }
            LimitPriceCommission commission = new LimitPriceCommission(String.valueOf(i),1, BigDecimal.ONE, direction);
            market.submit(commission);
        }
        stopWatch.stop();
        System.out.println(market.getLongBook().size());
        System.out.println(market.getShortBook().size());
        System.out.println(stopWatch.getTime());
        System.out.println("TPS: " + (count * 1000 / stopWatch.getTime()));
    }
}
