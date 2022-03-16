package dataStructures;

import java.io.Serializable;

public class DictionaryNode<K,V> implements Serializable{

    /**
     * Serial Version UID of the Class.
     */
    static final long serialVersionUID = 0L;

    /**
     * Entry stored in the node.
     */
    private EntryClass<K,V> entry;

    /**
     * (Pointer to) the previous node.
     *
     */
    private DictionaryNode<K,V> previous;

    /**
     * (Pointer to) the next node.
     *
     */
    private DictionaryNode<K,V> next;

    public DictionaryNode( K key, V value, DictionaryNode<K,V> thePrevious, DictionaryNode<K,V> theNext)
    {
        entry = new EntryClass<>(key, value);
        previous = thePrevious;
        next = theNext;
    }

    public DictionaryNode( K key, V value )
    {
        this(key, value, null, null);
    }


    /**
     *
     * @return the entry contained in the node
     */
    public EntryClass<K,V> getEntry( )
    {
        return entry;
    }

    public K getKey()
    {
        return entry.getKey();
    }

    public V getValue()
    {
        return entry.getValue();
    }

    /**
     *
     * @return the previous node
     */
    public DictionaryNode<K,V> getPrevious( )
    {
        return previous;
    }


    /**
     *
     * @return the next node
     */
    public DictionaryNode<K,V> getNext( )
    {
        return next;
    }


    public void setEntry( EntryClass<K,V> newEntry )
    {
        entry = newEntry;
    }

    public void setKey(K newKey)
    {
        entry.setKey(newKey);
    }

    public void setValue(V newValue)
    {
        entry.setValue(newValue);
    }

    public void setPrevious( DictionaryNode<K,V> newPrevious )
    {
        previous = newPrevious;
    }

    public void setNext( DictionaryNode<K,V> newNext )
    {
        next = newNext;
    }

}
