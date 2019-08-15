package com.iblotus.exchange;

import java.util.HashMap;
import java.util.Map;


/**
 * 默认
 */
public class DefaultMatcherProvider implements MatcherProvider {

    private static Map<String, CommissionMatcher> matcherMap = new HashMap<>();

    public DefaultMatcherProvider(CommissionBook longBook, CommissionBook shortBook){
        matcherMap.put("LIMIT_PRICE", new LimitPriceCommissionMatcher(longBook, shortBook));
    }

    @Override
    public CommissionMatcher findMatcher(String name) {
        if(name == null || "".equals(name)){
            return matcherMap.get("LIMIT_PRICE");
        }
        return matcherMap.get(name);
    }
}
