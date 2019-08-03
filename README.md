# match-making
交易撮合引擎

[项目地址](https://github.com/mkxzy/match-making)

maven:
```
<dependency>
    <groupId>com.iblotus.exchange</groupId>
    <artifactId>match-making</artifactId>
    <version>1.0</version>
</dependency>
```

```java
CommissionManager manager = new CommissionManager();
LimitPriceCommission commission1 =
                new LimitPriceCommission("a",BigDecimal.valueOf(10), 1, LongShort.Long);
LimitPriceCommission commission2 =
                new LimitPriceCommission("b",BigDecimal.valueOf(9), 1, LongShort.Short);
manager.submit(commission1);
manager.submit(commission2);
```
