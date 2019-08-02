package com.iblotus.exchange;

public interface OrderedList<T> {

    void add(T c);

    void remove(T c);

    T get(int index);

    boolean isEmpty();

    int size();

    T top();
}
