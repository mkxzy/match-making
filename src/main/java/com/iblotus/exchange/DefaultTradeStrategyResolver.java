package com.iblotus.exchange;


import java.util.HashMap;
import java.util.Map;


/**
 * 默认交易策略库
 */
public class DefaultTradeStrategyResolver implements TradeStrategyResolver {

    private Map<String, TradeStrategy> strategyMap = new HashMap<>();

    public DefaultTradeStrategyResolver(){
        strategyMap.put("LIMIT_PRICE", new LimitPriceStrategy());
    }

    @Override
    public TradeStrategy resolve(String name) {
        if(!strategyMap.containsKey(name)){
            throw new RuntimeException("Not supported strategy");
        }
        return strategyMap.get(name);
    }
}
