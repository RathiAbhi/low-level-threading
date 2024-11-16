package org.example;
import java.util.*;
public class CacheManager {
    List<CacheLevel> cacheLevels;
    Deque<Integer> readTimes;
    Deque<Integer> writeTimes;
    static final int MAX_HISTORY = 5;

    public CacheManager(){
        this.cacheLevels = new ArrayList<>();
        this.readTimes = new LinkedList<>();
        this.writeTimes = new LinkedList<>();
    }

    public void addCacheLevel(int capacity, int readTime, int writeTime){
        cacheLevels.add(new CacheLevel(capacity,readTime,writeTime));
    }

    public void read(String key){
        int totalTime = 0;
        String value = null;
        for (int i=0; i<cacheLevels.size(); i++){
            CacheLevel level = cacheLevels.get(i);
            totalTime += level.readTime;

            if (level.containsKey(key)){
                value = level.getValue(key);
                for (int j=0; j<i; j++){
                    CacheLevel higherLevel = cacheLevels.get(j);
                    if (!higherLevel.containsKey(key)){
                        higherLevel.putValue(key,value);
                        totalTime = higherLevel.writeTime;
                    }
                }
                break;
            }
        }

        if (value!=null){
            System.out.println("Value :"+value+", Total Read Time :"+totalTime);
            updateReadTimes(totalTime);
        } else {
            System.out.println("Key not found, Total Read Time :"+totalTime);
        }
    }

    public void write(String key, String value) {
        int totalTime = 0;
        for (CacheLevel level : cacheLevels) {
            totalTime += level.writeTime;

            if (!level.containsKey(key) || !level.getValue(key).equals(value)) {
                level.putValue(key, value);
            } else {
                break; // Stop writing if the value is already the same
            }
        }

        System.out.println("Total Write Time: " + totalTime);
        updateWriteTimes(totalTime);
    }

    public void stat() {
        System.out.println("Cache Usage:");
        for (int i = 0; i < cacheLevels.size(); i++) {
            CacheLevel level = cacheLevels.get(i);
            System.out.println("L" + (i + 1) + ": " + level.getUsage() + " / " + level.capacity);
        }

        System.out.println("Average Read Time (Last 5): " + getAverageTime(readTimes));
        System.out.println("Average Write Time (Last 5): " + getAverageTime(writeTimes));
    }

    private void updateReadTimes(int time) {
        if (readTimes.size() == MAX_HISTORY) {
            readTimes.pollFirst();
        }
        readTimes.addLast(time);
    }

    private void updateWriteTimes(int time) {
        if (writeTimes.size() == MAX_HISTORY) {
            writeTimes.pollFirst();
        }
        writeTimes.addLast(time);
    }

    private double getAverageTime(Deque<Integer> times) {
        if (times.isEmpty()) return 0;
        int sum = 0;
        for (int time : times) {
            sum += time;
        }
        return sum / (double) times.size();
    }

}
