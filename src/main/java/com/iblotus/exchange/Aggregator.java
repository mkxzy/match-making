package com.iblotus.exchange;

import java.util.List;
import java.util.Map;


/**
 * 聚合器
 * @param <Item>
 * @param <Key>
 */
public interface Aggregator<Item, Key> {

    Map<Key, List<Item>> aggregate(List<Item> commissions);
}
