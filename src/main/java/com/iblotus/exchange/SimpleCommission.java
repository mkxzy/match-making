package com.iblotus.exchange;


import java.math.BigDecimal;


/**
 * 限价委托
 * @param
 */
public class SimpleCommission implements Commission {

    private final String id;

    private final BigDecimal price;

    private final Side direction;

    private final long currentAmount;

    private final String strategy;

    public SimpleCommission(String id, BigDecimal price, long amount, Side direction){
        this(id, price, amount, direction, "LIMIT_PRICE");
    }

    public SimpleCommission(String id, BigDecimal price, long amount, Side direction, String strategy){
        this.currentAmount = amount;
        this.price = price;
        this.direction = direction;
        this.id = id;
        this.strategy = strategy;
    }

    /**
     * 委托ID
     * @return
     */
    @Override
    public String getId() {
        return this.id;
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
    @Override
    public long getAmount() {
        return this.currentAmount;
    }

    @Override
    public String getStrategy() {
        return this.strategy;
    }
}
