package dataStructures;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class BSTKeyOrderIterator<K, V> implements Iterator<Entry<K, V>> {

    Stack<BSTNode<K, V>> nodeStack;
    BSTNode<K, V> root;

    public BSTKeyOrderIterator(BSTNode<K, V> root) {
        nodeStack = new StackInList<>();
        this.root = root;
        initNodeStack();
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    @Override
    public Entry<K, V> next() throws NoSuchElementException {
        BSTNode<K, V> next = nodeStack.pop();
        if (next.getRight() != null) {
            BSTNode<K, V> left = next.getRight();

            while (left != null) {
                nodeStack.push(left);
                left = left.getLeft();
            }
        }

        return next.getEntry();
    }

    @Override
    public void rewind() {
        while (!nodeStack.isEmpty()) {
            nodeStack.pop();
        }
        initNodeStack();
    }

    private void initNodeStack() {
        while (root != null) {
            nodeStack.push(root);
            root = root.getLeft();
        }
    }
}
