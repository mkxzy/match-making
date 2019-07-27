package com.jrr.dfe;


import java.util.List;

/**
 * 委托定位策略
 */
interface CommissionLocateStrategy {

    /**
     * 查找任务的待插入索引
     * 如果没有合适的，返回-1
     * @param newCommition
     * @return
     */
    int locate(Commission newCommition);
}

/**
 * 定价优先
 */
class LowPriceFirstCommissionStategy implements CommissionLocateStrategy {

    private List<? extends Commission> list;

    LowPriceFirstCommissionStategy(List<? extends Commission> list) {
        this.list = list;
    }

    @Override
    public int locate(Commission newCommition) {
        int index = -1;
        for(int i = 0; i < list.size(); i++){
            Commission item = list.get(i);
            if(newCommition.getPrice().compareTo(item.getPrice()) <0){
                index = i;
                break;
            }
        }
        return index;
    }
}

/**
 * 高价优先
 */
class HighPriceFirstCommissionStrategy implements CommissionLocateStrategy {

    private List<? extends Commission> list;

    HighPriceFirstCommissionStrategy(List<? extends Commission> list) {
        this.list = list;
    }

    @Override
    public int locate(Commission newCommition) {
        int index = -1;
        for(int i = 0; i < list.size(); i++){
            Commission item = list.get(i);
            if(newCommition.getPrice().compareTo(item.getPrice()) > 0){
                index = i;
                break;
            }
        }
        return index;
    }
}
