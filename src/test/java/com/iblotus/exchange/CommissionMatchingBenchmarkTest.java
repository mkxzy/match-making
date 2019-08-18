package com.iblotus.exchange;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * 性能测试
 */
public class CommissionMatchingBenchmarkTest {

    private MatchMaker market = new MatchMaker();

    @Ignore
    @Test
    public void testBidBenchmark(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long count = 10000000;
        for(int i = 0; i < count; i++){
            Side direction;
            if(i % 2 == 0){
                direction = Side.Long;
            }else {
                direction = Side.Short;
            }
            SimpleCommission commission = new SimpleCommission(String.valueOf(i),BigDecimal.ONE, 1, direction);
            market.putOn(commission);
        }
        stopWatch.stop();
        System.out.println(market.getLong().size());
        System.out.println(market.getShort().size());
        System.out.println(stopWatch.getTime());
        System.out.println("TPS: " + (count * 1000 / stopWatch.getTime()));
    }
}
