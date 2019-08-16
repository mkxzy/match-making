package com.iblotus.exchange;


/**
 * 交易策略库
 */
public interface TradeStrategyResolver {

    /**
     * 检索策略
     * @param name
     * @return
     */
    TradeStrategy resolve(String name);
}
