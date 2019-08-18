package com.iblotus.exchange;


import java.util.HashMap;
import java.util.Map;

/**
 * 交易所
 */
public class CommissionExchanger {

    private DealHandler dealHandler;

    private final Map<String, MatchMaker> makers = new HashMap<>();

    public CommissionExchanger(DealHandler dealHandler) {
        this.dealHandler = dealHandler;
    }

    /**
     * 获取撮合器，如果不存在则新建
     * @param makerId
     * @return
     */
    public MatchMaker getMaker(String makerId){
        if(!makers.containsKey(makerId)){
            makers.put(makerId, new MatchMaker(dealHandler));
        }
        return makers.get(makerId);
    }
}
