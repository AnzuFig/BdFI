package dataStructures;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
class DictionaryIterator<K, V> implements TwoWayIterator<Entry<K, V>> {
    static final long serialVersionUID = 0L;

    protected DictionaryNode<K, V> firstEntry;
    protected DictionaryNode<K, V> lastEntry;
    protected DictionaryNode<K, V> nextToReturn;
    protected DictionaryNode<K, V> prevToReturn;

    public DictionaryIterator(DictionaryNode<K, V> first, DictionaryNode<K, V> last) {
        firstEntry = first;
        lastEntry = last;
        this.rewind();
    }

    @Override
    public void rewind() {
        nextToReturn = firstEntry;
        prevToReturn = null;
    }

    @Override
    public boolean hasNext() {
        return nextToReturn != null;
    }

    @Override
    public boolean hasPrevious() {
        return prevToReturn != null;
    }

    @Override
    public Entry<K, V> next() throws NoSuchElementException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        Entry<K, V> entry = nextToReturn.getEntry();
        prevToReturn = nextToReturn.getPrevious();
        nextToReturn = nextToReturn.getNext();
        return entry;
    }

    @Override
    public Entry<K, V> previous() throws NoSuchElementException {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }

        Entry<K, V> entry = prevToReturn.getEntry();
        nextToReturn = prevToReturn.getNext();
        prevToReturn = prevToReturn.getPrevious();
        return entry;
    }

    @Override
    public void fullForward() {
        prevToReturn = lastEntry;
        nextToReturn = null;
    }
}
