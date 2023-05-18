package ru.clevertec.newsservice.cache;

import java.util.Optional;

public interface Cache<Integer,T> {

    T put(Integer key, T value);

    T get(Integer key);

    T remove(Integer key);

    int size();

    boolean isEmpty();

    void clear();
}

