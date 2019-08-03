package com.iblotus.exchange;

import java.util.*;


/**
 * 委托挂单
 */
public class CommissionBook implements PendingBook<Commission> {

    private final List<Commission> list = new ArrayList<>();

    private final CommissionLocateStrategy strategy;

    private final List<CommissionBookListener> listeners = new ArrayList<>(2);

    private CommissionBook(final CommissionLocateStrategy strategy){
        this.strategy = strategy;
    }

    static CommissionBook HighFirst(){
        return new CommissionBook(new HighPriceFirstCommissionStrategy());
    }

    static CommissionBook LowFirst(){
        return new CommissionBook(new LowPriceFirstCommissionStategy());
    }

    public void addListener(CommissionBookListener listener){
        this.listeners.add(listener);
    }

    /**
     * 根据ID查找委托
     * @param id
     * @return
     */
    public Commission find(String id){
        for(Commission t: this.list){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    @Override
    public Commission top(){
        if(this.isEmpty()){
            throw new RuntimeException("OrderBook is empty");
        }
        return list.get(0);
    }


    /**
     * 添加到订单列表（排序）
     * @param c
     */
    @Override
    public int add(Commission c){
        int index = strategy.locate(c, this.list);
        if(index >= 0){
            list.add(index, c);
        }else{
            list.add(c);
            index = list.size() - 1;
        }
        for (CommissionBookListener listener: listeners) {
            listener.onAdd(this, c);
        }
        return index;
    }

    /**
     * 移除委托
     * @param c
     */
    @Override
    public void remove(Commission c){
        list.remove(c);
        for (CommissionBookListener listener: listeners) {
            listener.onRemove(this, c);
        }
    }

    @Override
    public Commission get(int index){
        return list.get(index);
    }

    @Override
    public boolean isEmpty(){
        return list.isEmpty();
    }

    @Override
    public int size(){
        return list.size();
    }

    public List<Commission> toList(){
        return Collections.unmodifiableList(this.list);
    }

    /**
     * 委托定位策略
     */
    private interface CommissionLocateStrategy {

        /**
         * 查找任务的待插入索引
         * 如果没有合适的，返回-1
         * @param newCommition
         * @return
         */
        int locate(Commission newCommition, List<Commission> list);
    }

    /**
     * 低价优先（二分查找法高性能）
     */
    private static class LowPriceFirstCommissionStategy implements CommissionLocateStrategy {

        @Override
        public int locate(Commission newCommition, List<Commission> list) {
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
        }
    }

    /**
     * 高价优先（二分查找法高性能）
     */
    private static class HighPriceFirstCommissionStrategy implements CommissionLocateStrategy {

        @Override
        public int locate(Commission newCommition, List<Commission> list) {
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
