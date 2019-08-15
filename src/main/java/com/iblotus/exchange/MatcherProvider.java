package com.iblotus.exchange;


/**
 * 委托匹配器提供器
 */
public interface MatcherProvider {

    /**
     * 查找匹配器
     * @param name
     * @return
     */
    CommissionMatcher findMatcher(String name);
}
