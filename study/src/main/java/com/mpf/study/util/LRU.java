package com.mpf.study.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU实现:最近最少使用
 *
 * @author mpf
 * @version 1.0
 * @date 2020/7/20 12:14
 */
public class LRU<K, V> {

    private static final float HASH_LOAD_FACTOR = 0.75f;
    private LinkedHashMap<K, V> map;
    private int cacheSize;

    public LRU(int cacheSize) {
        this.cacheSize = cacheSize;
        int capacity = (int) Math.ceil(cacheSize / HASH_LOAD_FACTOR) + 1;
        map = new LinkedHashMap<K, V>(capacity, HASH_LOAD_FACTOR, true) {
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > LRU.this.cacheSize;
            }
        };
    }

    public LRU(int cacheSize, int factor) {
        this.cacheSize = cacheSize;
        int capacity = (int) Math.ceil(cacheSize / factor) + 1;
        map = new LinkedHashMap<K, V>(capacity, factor, true) {
            private static final long serialVersionUID = 2;

            @Override
            protected boolean removeEldestEntry(Map.Entry entry) {
                return size() > LRU.this.cacheSize;
            }
        };
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized void clear() {
        map.clear();
    }

    public synchronized int usedSize() {
        return map.size();
    }

    public void print() {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.print(entry.getValue() + "--");
        }
        System.out.println();
    }
}
