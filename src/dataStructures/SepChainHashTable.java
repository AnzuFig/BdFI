package dataStructures;

import java.io.Serializable;

/**
 * Separate Chaining Hash table implementation
 *
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value
 * @author AED  Team
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 * @version 1.0
 */

public class SepChainHashTable<K extends Comparable<K>, V>
        extends HashTable<K, V> implements Serializable {
    /**
     * Serial Version UID of the Class.
     */
    static final long serialVersionUID = 0L;

    /**
     * The array of dictionaries.
     */
    protected Dictionary<K, V>[] table;


    /**
     * Constructor of an empty separate chaining hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     *
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
    public SepChainHashTable(int capacity) {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        // Compiler gives a warning.
        table = (Dictionary<K, V>[]) new Dictionary[arraySize];
        for (int i = 0; i < arraySize; i++)
            table[i] = new OrderedDoubleList<>();
        maxSize = capacity;
        currentSize = 0;

    }


    public SepChainHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Returns the hash value of the specified key.
     *
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public V find(K key) {
        return table[this.hash(key)].find(key);
    }

    @Override
    public V insert(K key, V value) {
        if (this.isFull()) {
            this.rehash();
        }
        int index = this.hash(key);
        V valueToReturn = table[index].insert(key, value);
        if (valueToReturn == null) currentSize++;
        return valueToReturn;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        maxSize *= 2;
        int newSize = HashTable.nextPrime((int) (1.1 * maxSize));
        Dictionary<K, V>[] newTable = (Dictionary<K, V>[]) new Dictionary[newSize];
        for (int i = 0; i < newSize; i++) {
            newTable[i] = new OrderedDoubleList<>();
        }
        Dictionary<K, V> dictionary;
        int index;
        for (int j = 0; j < table.length; j++) {
            dictionary = table[j];
            if (dictionary != null) {
                Iterator<Entry<K, V>> it = dictionary.iterator();
                while (it.hasNext()) {
                    Entry<K, V> next = it.next();
                    if (next != null) {
                        index = Math.abs(next.getKey().hashCode()) % newTable.length; // new hash
                        newTable[index].insert(next.getKey(), next.getValue());
                    }
                }
            }
        }
        table = newTable;
    }

    @Override
    public V remove(K key) {
        int index = this.hash(key);
        V value = table[index].remove(key);
        if (value != null) currentSize--;
        return value;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return getFullDictionary().iterator();
    }

    private Dictionary<K, V> getFullDictionary() {
        Dictionary<K, V> fullDictionary = new OrderedDoubleList<>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Iterator<Entry<K, V>> it = table[i].iterator();
                while (it.hasNext()) {
                    Entry<K, V> next = it.next();
                    if (next != null) {
                        fullDictionary.insert(next.getKey(), next.getValue());
                    }
                }
            }
        }
        return fullDictionary;
    }

}
