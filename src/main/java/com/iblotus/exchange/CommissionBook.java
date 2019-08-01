package com.iblotus.exchange;

import java.util.ArrayList;
import java.util.List;


/**
 * 委托挂单
 */
public class CommissionBook<T extends Commission> {

    private final List<CommissionRecorder<T>> list = new ArrayList<>();

    private final CommissionLocateStrategy<T> strategy;

    private CommissionBook(final CommissionLocateStrategy<T> strategy){
        this.strategy = strategy;
    }

    static <T extends Commission> CommissionBook<T> HighFirst(){
        return new CommissionBook<>(new HighPriceFirstCommissionStrategy<T>());
    }

    static <T extends Commission> CommissionBook<T> LowFirst(){
        return new CommissionBook<>(new LowPriceFirstCommissionStategy<T>());
    }

    /**
     * 添加到订单列表（排序）
     * @param c
     */
    public int add(CommissionRecorder<T> c){
        int index = strategy.locate(c, this.list);
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

    /**
     * 委托定位策略
     */
    private interface CommissionLocateStrategy<T extends Commission> {

        /**
         * 查找任务的待插入索引
         * 如果没有合适的，返回-1
         * @param newCommition
         * @return
         */
        int locate(CommissionRecorder<T> newCommition, List<CommissionRecorder<T>> list);
    }

    /**
     * 低价优先（二分查找法高性能）
     */
    private static class LowPriceFirstCommissionStategy<T extends Commission> implements CommissionLocateStrategy<T> {

        @Override
        public int locate(CommissionRecorder<T> newCommition, List<CommissionRecorder<T>> list) {
            int mid;
            int start = 0;
            int end = list.size() - 1;
            while (start <= end){
                mid = (end - start) / 2 + start;
                if(newCommition.getPrice().compareTo(list.get(mid).getPrice()) < 0){
                    if(mid == 0){
                        return 0;
                    }
                    if(newCommition.getPrice().compareTo(list.get(mid-1).getPrice()) >= 0){
                        return mid;
                    }
                    end = mid - 1;
                } else {
                    if(mid > end){
                        return -1;
                    }
                    if(newCommition.getPrice().compareTo(list.get(mid).getPrice()) < 0){
                        return mid;
                    }
                    start = mid + 1;
                }
            }
            return -1;
//            for(int i = 0; i < list.size(); i++){
//                CommissionRecorder<T> item = list.get(i);
//                if(newCommition.getPrice().compareTo(item.getPrice()) <0){
//                    index = i;
//                    break;
//                }
//            }
//            return index;
        }
    }

    /**
     * 高价优先（二分查找法高性能）
     */
    private static class HighPriceFirstCommissionStrategy<T extends Commission> implements CommissionLocateStrategy<T> {

        @Override
        public int locate(CommissionRecorder<T> newCommition, List<CommissionRecorder<T>> list) {
            int mid;
            int start = 0;
            int end = list.size() - 1;
            while (start <= end){
                mid = (end - start) / 2 + start;
                if(newCommition.getPrice().compareTo(list.get(mid).getPrice()) > 0){
                    if(mid == 0){
                        return 0;
                    }
                    if(newCommition.getPrice().compareTo(list.get(mid-1).getPrice()) <= 0){
                        return mid;
                    }
                    end = mid - 1;
                } else {
                    if(mid > end){
                        return -1;
                    }
                    if(newCommition.getPrice().compareTo(list.get(mid).getPrice()) > 0){
                        return mid;
                    }
                    start = mid + 1;
                }
            }
            return -1;
        }
    }
}
