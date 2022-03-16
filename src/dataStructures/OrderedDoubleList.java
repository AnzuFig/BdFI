package dataStructures;

public class OrderedDoubleList<K extends Comparable<K>, V> implements OrderedDictionary<K, V> {


    protected DictionaryNode<K, V> head;
    protected DictionaryNode<K, V> tail;

    protected int currentSize;

    public OrderedDoubleList() {
        head = null;
        tail = null;
        currentSize = 0;
    }

    @Override
    public Entry<K, V> minEntry() throws EmptyDictionaryException {
        return head.getEntry();
    }

    @Override
    public Entry<K, V> maxEntry() throws EmptyDictionaryException {
        return tail.getEntry();
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public V find(K key) {
        DictionaryNode<K,V> node = findNode(key);
        V value = null;
        if(node != null){value = node.getValue();}
        return value;
    }

    private DictionaryNode<K, V> findNode(K key) {
        DictionaryNode<K, V> node = head;
        while (node != null && !node.getKey().equals(key)) {
            node = node.getNext();
        }
        return node;
    }

    @Override
    public V insert(K key, V value) {
        DictionaryNode<K,V> node = findNode(key);
        V returnValue = null;
        if (node != null) {
            returnValue = node.getValue();
            node.setValue(value);
        }else{
            node = head;
            if(currentSize != 0){
                while(node != null && !(node.getKey().compareTo(key) > 0)){
                    node = node.getNext();
                }
                DictionaryNode<K,V> nodeToAdd;
                if(node == head){
                    nodeToAdd = new DictionaryNode<K,V>(key, value, null, node);
                    head = nodeToAdd;
                    head.setNext(node);
                    node.setPrevious(head);
                }
                else if(node == null){
                    nodeToAdd = new DictionaryNode<K,V>(key, value, tail, null);
                    tail.setNext(nodeToAdd);
                    tail = nodeToAdd;
                }
                else{
                    nodeToAdd = new DictionaryNode<K,V>(key, value, node.getPrevious(), node);
                    node.getPrevious().setNext(nodeToAdd);
                    node.setPrevious(nodeToAdd);
                }
            }
            else{
                DictionaryNode<K,V> nodeToAdd = new DictionaryNode<K,V>(key, value, null, null);
                head = nodeToAdd;
                tail = nodeToAdd;
            }
            currentSize++;
        }
        return returnValue;

    }

    @Override
    public V remove(K key) {
        DictionaryNode<K, V> node = findNode(key);
        V value = null;
        if (node != null) {
            if(node == head){
                if(currentSize > 1){
                    head = head.getNext();
                    head.setPrevious(null);
                }
                else{
                    head = null;
                    tail = null;
                }
            }
            else if(node == tail){
                tail = tail.getPrevious();
                tail.setNext(null);
            }
            else{
                node.getPrevious().setNext(node.getNext());
                node.getNext().setPrevious(node.getPrevious());
            }
            currentSize--;

            value = node.getValue();
        }

        return value;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator<K, V>(head, tail);
    }
}
