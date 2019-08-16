package com.iblotus.exchange;

import java.util.*;


/**
 * 委托挂单
 */
public class CommissionBook implements PendingBook<PendingCommission> {

    private final List<PendingCommission> list = new ArrayList<>();

    private final CommissionBookIndexLocator strategy;

//    private final List<CommissionBookListener> listeners = new ArrayList<>(2);

    public CommissionBook(final CommissionBookIndexLocator strategy){
        this.strategy = strategy;
    }

    static CommissionBook HighFirst(){
        return new CommissionBook(new HighPriceFirstCommissionStrategy());
    }

    static CommissionBook LowFirst(){
        return new CommissionBook(new LowPriceFirstCommissionStategy());
    }

//    void addListener(CommissionBookListener listener){
//        this.listeners.add(listener);
//    }

    /**
     * 根据ID查找委托
     * @param id
     * @return
     */
    public PendingCommission find(String id){
        for(PendingCommission t: this.list){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    @Override
    public PendingCommission top(){
        if(this.isEmpty()){
            throw new RuntimeException("OrderBook is empty");
        }
        return list.get(0);
    }


    /**
     * 添加到订单列表（排序）
     * @param c 委托
     *
     * @return 档位索引（从0开始）
     */
    @Override
    public int add(PendingCommission c){
        int index = strategy.findProperIndex(c, this.list);
        if(index >= 0){
            list.add(index, c);
        }else{
            list.add(c);
            index = list.size() - 1;
        }
//        for (CommissionBookListener listener: listeners) {
//            listener.onAdd(this, c);
//        }
        return index;
    }

    /**
     * 移除委托
     * @param c
     */
    @Override
    public void remove(PendingCommission c){
        list.remove(c);
//        for (CommissionBookListener listener: listeners) {
//            listener.onRemove(this, c);
//        }
    }

    @Override
    public PendingCommission get(int index){
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

    @Override
    public List<PendingCommission> toList(){
        return Collections.unmodifiableList(this.list);
    }

    /**
     * 低价优先（二分查找法高性能）
     */
    private static class LowPriceFirstCommissionStategy implements CommissionBookIndexLocator {

        @Override
        public int findProperIndex(PendingCommission commission, List<PendingCommission> list) {
            int mid;
            int start = 0;
            int end = list.size() - 1;
            while (start <= end){
                mid = (end - start) / 2 + start;
                if(commission.getPrice().compareTo(list.get(mid).getPrice()) < 0){
                    if(mid == 0){
                        return 0;
                    }
                    if(commission.getPrice().compareTo(list.get(mid-1).getPrice()) >= 0){
                        return mid;
                    }
                    end = mid - 1;
                } else {
                    if(mid > end){
                        return -1;
                    }
                    if(commission.getPrice().compareTo(list.get(mid).getPrice()) < 0){
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
    private static class HighPriceFirstCommissionStrategy implements CommissionBookIndexLocator {

        @Override
        public int findProperIndex(PendingCommission commission, List<PendingCommission> list) {
            int mid;
            int start = 0;
            int end = list.size() - 1;
            while (start <= end){
                mid = (end - start) / 2 + start;
                if(commission.getPrice().compareTo(list.get(mid).getPrice()) > 0){
                    if(mid == 0){
                        return 0;
                    }
                    if(commission.getPrice().compareTo(list.get(mid-1).getPrice()) <= 0){
                        return mid;
                    }
                    end = mid - 1;
                } else {
                    if(mid > end){
                        return -1;
                    }
                    if(commission.getPrice().compareTo(list.get(mid).getPrice()) > 0){
                        return mid;
                    }
                    start = mid + 1;
                }
            }
            return -1;
        }
    }
}
