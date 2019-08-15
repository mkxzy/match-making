package com.iblotus.exchange;


import java.util.List;

/**
 * 挂单接口
 * @param <T>
 */
public interface PendingBook<T> {

    /**
     * 新增
     * @param c
     * @return
     */
    int add(T c);

    /**
     * 删除
     * @param c
     */
    void remove(T c);

    /**
     * 查询
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 获取第一个元素
     * @return
     */
    T top();

    /**
     * 获取长度
     * @return
     */
    int size();

    /**
     * 判断是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 挂单列表
     * @return
     */
    List<T> toList();
}
