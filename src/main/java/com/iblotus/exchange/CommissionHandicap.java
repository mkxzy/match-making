package com.iblotus.exchange;


import com.iblotus.exchange.exceptions.CommissionNotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 委托盘口
 */
public class CommissionHandicap {

    // 买盘
    private final CommissionBook longBook = CommissionBook.HighFirst();

    // 卖盘
    private final CommissionBook shortBook = CommissionBook.LowFirst();

    // 委托归属
    private final Map<String, CommissionBook> commissionBelong = new HashMap<>();

    public void add(PendingCommission commission){
        if(commission.getDirection() == Side.Long){
            longBook.add(commission);
            commissionBelong.put(commission.getId(), longBook);
        }else {
            shortBook.add(commission);
            commissionBelong.put(commission.getId(), shortBook);
        }
    }

    public void remove(PendingCommission commission){
        if(commission.getDirection() == Side.Long){
            longBook.remove(commission);
        }else {
            shortBook.remove(commission);
        }
        commissionBelong.remove(commission.getId());
    }

    public PendingCommission opponentTop(PendingCommission commission){
        if(commission.getDirection() == Side.Long){
            return shortBook.top();
        }else {
            return longBook.top();
        }
    }

    public boolean isOpponentEmpty(PendingCommission commission){
        if(commission.getDirection() == Side.Long){
            return shortBook.isEmpty();
        }else {
            return longBook.isEmpty();
        }
    }

    public void removeById(String id){
        CommissionBook commissionBook = commissionBelong.get(id);
        if(commissionBook != null){
            commissionBelong.remove(id);
            PendingCommission commission = commissionBook.find(id);
            if(commission != null){
                commissionBook.remove(commission);
                return;
            }
        }
        throw new CommissionNotExistException();
    }

    public boolean contains(String id){
        return commissionBelong.containsKey(id);
    }

    public List<PendingCommission> getLongCommissions(){
        return this.longBook.toList();
    }

    public List<PendingCommission> getShortCommissions(){
        return this.shortBook.toList();
    }
}
