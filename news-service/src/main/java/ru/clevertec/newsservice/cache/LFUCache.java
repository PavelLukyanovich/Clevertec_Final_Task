package ru.clevertec.newsservice.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class implements LFU cache.
 *
 * @param <T>
 * @Version 1.0
 */

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class LFUCache<T> implements Cache<Integer, T> {

/**
     * LinkedHashMap is used for storing data in the cache as a key
     * value of Integer type, and Node is used as a value.
     *
     * @see Node
     */

    private final Map<Integer, Node> cacheStorage = new LinkedHashMap<>();

/**
     * The size of the cache is set during configuration from the application.yml file.
     */

    @Value("${cache.capacity}")
    private int cacheCapacity;


/**
     * Method of adding data to the cache.
     * First, we check if the cache is full. If there is space, the data is added.
     * If the cache is full, then first the key of the item that should be removed from
     * the cache is calculated (which frequency of use is the lowest), then using this key you delete
     * element from the cache and place a new element in the cache.
     *
     * @param key
     * @param value
     * @return T value
     */


    @Override
    public T put(Integer key, T value) {

        if (cacheStorage.size() < cacheCapacity) {
            cacheStorage.put(key, new Node(value));
        }

        long minFrequency = Long.MAX_VALUE;
        Integer keyToRemove = null;

        for (Map.Entry<Integer, Node> entry : cacheStorage.entrySet()) {
            if (Objects.equals(entry.getKey(), key)) {

            }

            if (minFrequency >= entry.getValue().getFrequency()) {
                minFrequency = entry.getValue().getFrequency();
                keyToRemove = entry.getKey();
            }
        }
        cacheStorage.remove(keyToRemove);
        return (T) cacheStorage.put(key, new Node(value));
    }


/**
     * The method of getting data from the cache by the key.
     * We need to count the number of times we accessed the objects in the cache, when we call this method
     * increases counter of calls to cache item. After that we directly retrieve the element itself.
     *
     * @param key
     * @return T value
     */

    @Override
    public T get(Integer key) {

        Node node = cacheStorage.get(key);
        if (Objects.isNull(node)) {
            return null;
        }
        return (T) node.incrementFrequency().getValue();

    }


/**
     * Remove cache element method.
     *
     * @param key
     * @return T value
     */

    @Override
    public T remove(Integer key) {
        return (T) cacheStorage.remove(key);
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

/**
     * A class describing a node entity. It contains the data itself and the access counter.
     * There is also a method of increasing the access counter.
     *
     * @param <T>
     */

    @Data
    private class Node<T> {

        private final T value;

        public long frequency;
        public Node(T value) {
            this.value = value;
            this.frequency = 1;
        }

        public Node incrementFrequency() {
            ++frequency;
            return this;
        }

    }
}


