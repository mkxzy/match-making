package com.jrr.dfe;


/**
 * 委托成交处理
 */
interface DealMaker<T extends Commission> {

    /**
     * 判断是否能够撮合
     * @param opponentMission
     * @return
     */
    boolean canDeal(CommissionRecorder<T> opponentMission);

    /**
     * 撮合
     * @param opponentMission
     * @return
     */
    Deal<T> makeDeal(CommissionRecorder<T> opponentMission);
}


