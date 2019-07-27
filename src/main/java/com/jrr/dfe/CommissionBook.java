package com.jrr.dfe;

import java.util.ArrayList;
import java.util.List;


/**
 * 挂单
 */
public class CommissionBook {

    private List<CommissionRecorder> list = new ArrayList<>();

    private CommissionSortMode commissionSort;

    private CommissionLocateStrategy strategy;

    public CommissionBook(CommissionSortMode commissionSort){
        this.commissionSort = commissionSort;
        if(this.commissionSort == CommissionSortMode.LowPriceFirst){
            strategy = new LowPriceFirstCommissionStategy(this.list);
        }else if(this.commissionSort == CommissionSortMode.HighPriceFirst){
            strategy = new HighPriceFirstCommissionStrategy(this.list);
        }else {
            throw new RuntimeException("CommissionSortMode Invalid");
        }
    }

    /**
     * 添加到订单列表（排序）
     * @param c
     */
    public int add(CommissionRecorder c){
        int index = strategy.locate(c);
        if(index >= 0){
            list.add(index, c);
            return index;
        }else{
            list.add(c);
            return list.size() - 1;
        }
    }

    public void remove(Commission c){
        list.remove(c);
    }

    /**
     * 根据索引获取订单项
     * @param index
     * @return
     */
    public CommissionRecorder get(int index){
        return list.get(index);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public int size(){
        return list.size();
    }

    public CommissionRecorder head(){
        if(this.isEmpty()){
            throw new RuntimeException("OrderBook is empty");
        }
        return list.get(0);
    }
}
