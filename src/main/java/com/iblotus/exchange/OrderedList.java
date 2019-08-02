package com.iblotus.exchange;


/**
 * 排序列表
 * @param <T>
 */
public interface OrderedList<T> {

    /**
     * 加入列表，位置由列表控制
     * @param c
     * @return
     */
    int add(T c);

    /**
     * 移除元素
     * @param c
     */
    void remove(T c);

    /**
     * 获取元素
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 数量
     * @return
     */
    int size();
}
