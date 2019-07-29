package com.jrr.dfe;

import java.util.ArrayList;
import java.util.List;


/**
 * 委托挂单
 */
public class CommissionBook<T extends Commission> {

    private final List<CommissionRecorder<T>> list = new ArrayList<>();

    private CommissionLocateStrategy strategy;

    public CommissionBook(CommissionSortMode commissionSort){
        if(commissionSort == CommissionSortMode.LowPriceFirst){
            strategy = new LowPriceFirstCommissionStategy(this.list);
        }else if(commissionSort == CommissionSortMode.HighPriceFirst){
            strategy = new HighPriceFirstCommissionStrategy(this.list);
        }else {
            throw new RuntimeException("CommissionSortMode Invalid");
        }
    }

    /**
     * 添加到订单列表（排序）
     * @param c
     */
    public int add(CommissionRecorder<T> c){
        int index = strategy.locate(c);
        if(index >= 0){
            list.add(index, c);
            return index;
        }else{
            list.add(c);
            return list.size() - 1;
        }
    }

    public void remove(CommissionRecorder<T> c){
        list.remove(c);
    }

    /**
     * 根据索引获取订单项
     * @param index
     * @return
     */
    public CommissionRecorder<T> get(int index){
        return list.get(index);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public int size(){
        return list.size();
    }

    public CommissionRecorder<T> head(){
        if(this.isEmpty()){
            throw new RuntimeException("OrderBook is empty");
        }
        return list.get(0);
    }
}
