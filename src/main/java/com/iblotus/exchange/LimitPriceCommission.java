package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 限价委托
 * @param
 */
public class LimitPriceCommission implements Commission {

    private final String id;

    private final BigDecimal price;

    private final Side direction;

    private long currentAmount;

    public LimitPriceCommission(String id, BigDecimal price, long amount, Side direction){
        this.currentAmount = amount;
        this.price = price;
        this.direction = direction;
        this.id = id;
    }

    /**
     * 价格
     */
    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 多空
     */
    @Override
    public Side getDirection() {
        return this.direction;
    }

    /**
     * 获取当前数量
     */
    public long getAmount() {
        return this.currentAmount;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getMatcher() {
        return "LIMIT_PRICE";
    }
}
