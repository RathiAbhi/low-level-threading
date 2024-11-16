package org.example;

import java.util.HashMap;
import java.util.Map;

public class CacheLevel {
    int capacity;
    int readTime;
    int writeTime;
    Map<String,String> storage;
    int currentSize;

    public CacheLevel(int capacity, int readTime, int writeTime){
        this.capacity = capacity;
        this.readTime = readTime;
        this.writeTime = writeTime;
        this.storage = new HashMap<>();
        this.currentSize = 0;
    }

    public boolean containsKey(String key){
        return storage.containsKey(key);
    }

    public String getValue(String key){
        return storage.get(key);
    }

    public void putValue(String key, String value){
        if (currentSize < capacity){
            storage.put(key,value);
            currentSize++;
        } else {
            System.out.println("Cache is full, consider implementing an eviction policy");
        }
    }

    public int getUsage(){
        return currentSize;
    }
}
