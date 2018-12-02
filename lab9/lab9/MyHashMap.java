package lab9;

import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Haochao Zhu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int initialSize) {
        buckets = new ArrayMap[initialSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int bucketNumber = hash(key);
        return buckets[bucketNumber].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int bucketNumber = hash(key);

        if (buckets[bucketNumber].containsKey(key)) {
            buckets[bucketNumber].remove(key);
            size -= 1;
            buckets[bucketNumber].put(key, value);
        } else buckets[bucketNumber].put(key, value);

        size += 1;

        if (loadFactor() > MAX_LF) {
            resize();
        }
    }

    private void resize() {
//        int tempSize = buckets.length * 2;
//        MyHashMap<K, V> temp = new MyHashMap<K, V>(tempSize);
//        for (int i =0; i < buckets.length; i++) {
//            for (K key: buckets[i].keySet()) {
//                temp.put(key, buckets[i].get(key));
//            }
//        }
//        this.buckets = temp.buckets;

        ArrayMap<K, V>[] old = buckets;
        buckets = new ArrayMap[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            for (K key: old[i].keySet()) {
                put(key, old[i].get(key));
            }
        }

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> ks = new HashSet<>();
        for (int i =0; i < buckets.length; i++) {
            for (K key: buckets[i].keySet()) {
                ks.add(key);
            }
        }
        return ks;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {

        int bucketNumber = hash(key);
        V result = buckets[bucketNumber].get(key);
        buckets[bucketNumber].remove(key);

        return result;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyHashMap<Integer, String> dict = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            dict.put(i, "Joe");
        }

        StdOut.println(dict.size());
    }
}
