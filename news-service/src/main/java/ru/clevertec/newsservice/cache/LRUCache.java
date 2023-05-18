package ru.clevertec.newsservice.cache;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Class implements LRU cache.
 *
 * @param <T>
 * @Version 1.0
 */

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class LRUCache<T> implements Cache<Integer, T> {

/**
     * To store data in the cache we use HashMap as a key
     * for which is a value of type Integer, and an object of type T as a value.
     */

    private final Map<Integer, T> cacheStorage = new HashMap<>();

/**
     * Allows you to track the order of elements in the cache. The element that was accessed is at the top of the list.
     * Gets to the beginning of the list, respectively the one which has not been accessed for a long time is closer to the end.
     * Thus, we have the possibility to trace the most recently accessed element
     * in relation to other elements.
     */


    private final List<Integer> order = new LinkedList<>();

/**
     * The size of the cache is set during configuration from the application.yml file.
     */

    @Value("${cache.capacity}")
    private int cacheCapacity;


/**
     * The method of adding data to the cache.
     * First, we check if the cache is full. If the cache is full, then first it is calculated
     * key of the element to be removed from cache (last element in the order), then using the obtained
     * last element in the order, then the * key is calculated, and the element is removed from the cache.
     * After that the key of the new element is placed in the first position of the order.
     * Then the new element is added to the cache.
     *
     * @param key
     * @param value
     * @return T value
     */


    @Override
    public T put(Integer key, T value) {

        if (order.size() >= cacheCapacity) {

            Integer keyRemoved = order.remove(order.size() - 1);
            cacheStorage.remove(keyRemoved);
        }
        order.add(0, key);
        return cacheStorage.put(key, value);
    }


/**
     * The method of obtaining data from the cache by key.
     * Gets data from the cache and overwrites the position of the key by which the data was accessed.
     * The key is removed from the current position in the order and placed at the very beginning.
     *
     * @param key
     * @return T value
     */

    @Override
    public T get(Integer key) {

        T value = (T) cacheStorage.get(key);
        if (Objects.nonNull(value)) {

            order.remove(key);
            order.add(0, key);
            return value;
        }
        return null;
    }


/**
     * Remove cache element method.
     *
     * @param key
     * @return T value
     */

    @Override
    public T remove(Integer key) {
        order.remove(key);

        return cacheStorage.remove(key);
    }

    @Override
    public int size() {
        return cacheStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return cacheStorage.isEmpty();
    }

    @Override
    public void clear() {
        cacheStorage.clear();
    }
}

