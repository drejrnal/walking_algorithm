package designProblem;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * @Author:毛翔宇
 * @Date 2019-03-08 16:05
 */
public class LFUCacheConcise {
    private HashMap<Integer,Integer> keyToValue;
    private HashMap<Integer,Integer> keyToFrequency;
    private HashMap<Integer, LinkedHashSet<Integer>> frequencyToBucket;
    int size;
    int capacity;
    private int minFrequency = -1;
    public LFUCacheConcise(int _cap){
        keyToValue = new HashMap<>();
        keyToFrequency = new HashMap<>();
        frequencyToBucket = new HashMap<>();
        frequencyToBucket.put(1,new LinkedHashSet<>());
        size = 0;
        capacity = _cap;
    }
    public int get(int key){
        if (keyToValue.containsKey(key)){
            int frequency = keyToFrequency.get(key);
            keyToFrequency.put(key,frequency+1);
            frequencyToBucket.get(frequency).remove(key);
            if (!frequencyToBucket.containsKey(frequency+1))
                frequencyToBucket.put(frequency+1,new LinkedHashSet<>());
            frequencyToBucket.get(frequency+1).add(key);
            if (minFrequency == frequency && frequencyToBucket.get(frequency).isEmpty())
                minFrequency +=1;
            return keyToValue.get(key);
        }
        return -1;
    }
    public void put(int key,int value){
        if (capacity <=0)return;
        if (keyToValue.containsKey(key)){
            keyToValue.put(key,value);
            get(key);
            return;
        }
        if (size == capacity){
            int evict = frequencyToBucket.get(minFrequency).iterator().next();
            frequencyToBucket.get(minFrequency).remove(evict);
            keyToValue.remove(evict);
        }
        else
            size+=1;
        keyToValue.put(key,value);
        keyToFrequency.put(key,1);
        minFrequency = 1;
        frequencyToBucket.get(minFrequency).add(key);
    }
    public static void main(String[] args){
        LFUCache cache = new LFUCache( 0 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println(cache.get(2));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3.
        cache.put(4, 4);    // evicts key 1.
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4
    }
}
