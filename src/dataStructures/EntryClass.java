package dataStructures;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class EntryClass<K,V> implements Entry<K,V>{

    private K key;
    private V value;

    public EntryClass(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
;