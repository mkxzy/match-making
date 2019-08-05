# match-making
交易撮合引擎

[项目地址](https://github.com/mkxzy/match-making)

Maven：
```
<dependency>
    <groupId>com.iblotus.exchange</groupId>
    <artifactId>match-making</artifactId>
    <version>1.1</version>
</dependency>
```

示例：
```java
DealHandler dealHandler = new DealHandler() {
    @Override
    public void onDeal(Deal<Commission> deal) {
        System.out.printf("%f, %s, %s, %s\n",
                deal.getPrice(),
                deal.getAmount(),
                deal.getInitiate().getId(),
                deal.getPassive().getId());
    }
};
CommissionManager manager = new CommissionManager(dealHandler);
LimitPriceCommission commission1 =
        new LimitPriceCommission("a", BigDecimal.valueOf(10), 1, LongShort.Long);
LimitPriceCommission commission2 =
        new LimitPriceCommission("b",BigDecimal.valueOf(11), 1, LongShort.Long);
LimitPriceCommission commission3 =
        new LimitPriceCommission("c",BigDecimal.valueOf(9), 2, LongShort.Short);
manager.submit(commission1);
manager.submit(commission2);
manager.submit(commission3);
```
