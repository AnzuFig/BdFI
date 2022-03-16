package dataStructures;

public class InvertibleQueueClass<E> extends QueueInList<E> implements InvertibleQueue<E> {

	/**
	 * Serial Version UID of the Class
	 */
    static final long serialVersionUID = 0L;
	
	public InvertibleQueueClass() {
		super();
	}

	@Override
	public void invert() {
		for(int i = 0; i < size(); i++) {
			E element = this.dequeue();
			this.enqueue(element);
		}
	}

	
}
