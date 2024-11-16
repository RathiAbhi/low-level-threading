package org.example;

public class Main {
    public static void main(String[] args) {
        CacheManager cacheManager = new CacheManager();

        // Adding cache levels (capacity, readTime, writeTime)
        cacheManager.addCacheLevel(3, 1, 2); // L1
        cacheManager.addCacheLevel(5, 2, 3); // L2
        cacheManager.addCacheLevel(10, 3, 4); // L3

        // Testing WRITE operation
        cacheManager.write("key1", "value1");
        cacheManager.write("key2", "value2");
        cacheManager.write("key1", "value1"); // Should not rewrite since value is the same

        // Testing READ operation
        cacheManager.read("key1");
        cacheManager.read("key2");
        cacheManager.read("key3"); // Key not present

        // Printing statistics
        cacheManager.stat();
    }
}