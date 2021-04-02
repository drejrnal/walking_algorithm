package DataStructures;

public abstract class HashTableOpenAddressBase<K,V>  {

    private static final int DEFAULT_CAPACITY = 11;
    private static final double DEFAULT_FACTOR_LOAD = 0.75;
    private int capacity, modificationCnt;
    private double load_factor;
    private K[] keys;
    private V[] values;
    private final K TOMBSTONE = (K)new Object();

    /*
    usedBuckets表示当前哈希表中有多少槽位被占用（包括逻辑删除的节点）
    keyCount表示目前哈希表中不同键的个数
    threshold表示哈希表需要扩容的阈值
     */
    private int usedBuckets, keyCount;
    private double threshold;

    protected abstract int probe(int iteration);

    /*
    helper function
     */
    protected void resizeTable(){}
    protected int normalize(int hash){
        return (hash & 0x7fffffff) % capacity;
    }

    public V insert(K key, V value){
        if (key == null) throw new IllegalArgumentException("key can't be NULL");
        if (usedBuckets == threshold)
            resizeTable();
        final int hash1 = normalize(key.hashCode());
        int previousDeletion = -1;
        for (int i = 1,slot = hash1; ;slot = normalize(hash1+probe(i++))){
            if (keys[slot] == TOMBSTONE){
                if (previousDeletion == -1)
                    previousDeletion = slot;
            }else if (keys[slot] != null){
                if (keys[slot].equals(key)){
                    V oldValue = values[slot];
                    if (previousDeletion != -1){
                        keys[previousDeletion] = key;
                        values[previousDeletion] = value;
                        keys[slot] = TOMBSTONE;
                        values[slot] = null;
                    }else
                        values[slot] = value;
                    modificationCnt++;
                    return oldValue;
                }
            }else {
                if (previousDeletion != -1){
                    keys[previousDeletion] = key;
                    values[previousDeletion] = value;
                }else {
                    keys[slot] = key;
                    values[slot] = value;
                    usedBuckets ++;
                }
                keyCount++;
                modificationCnt++;
                return null;
            }
        }
    }
    public V search(K key){
        if (key == null) throw new IllegalArgumentException("key can't be NULL");
        final int hash1 = normalize(key.hashCode());
        int previousDelete = -1;
        for (int slot = hash1, i = 1; ; slot = normalize(hash1 + probe(i++))){
            if (keys[slot] == TOMBSTONE){
                if (previousDelete == -1)
                    previousDelete = slot;
            }else if (keys[slot] != null){
                if (keys[slot].equals(key)) {
                    V val = values[slot];
                    if (previousDelete != -1) {
                        keys[previousDelete] = keys[slot];
                        values[previousDelete] = values[slot];
                        keys[slot] = TOMBSTONE; // 注意这里仍然使用TOMBSTONE进行标记，因为该位置也可能是其他键的探寻路径的某一节点
                        values[slot] = null;
                    }
                    return val;
                }
            }else
                return null;
        }
    }
    public V remove(K key){
        if (key == null) throw new IllegalArgumentException("key can't be NULL");
        final int hash1 = normalize(key.hashCode());
        for (int slot = hash1, i=1; ; slot = normalize(hash1 + probe(i++))){
            if (keys[slot] == null) return null;
            if (keys[slot].equals(key)){
                V oldVal = values[slot];
                keyCount--;
                keys[slot] = TOMBSTONE;
                values[slot] = null;
                return oldVal;
            }
        }
    }


}
