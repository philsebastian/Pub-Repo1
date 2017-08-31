import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author Phillip Sebastian
 *
 */
public class Cache<T> implements ICache<T> {
	private int cacheSize;
	private int MAX_SIZE;
	private int hits;
	private int access;
	private DLLNode<T> head;
	private DLLNode<T> tail;
	
	public Cache(int maxSize){
		this.MAX_SIZE = maxSize;
		this.cacheSize = 0;
		this.hits = 0;
		this.access = 0;
		this.head = null;
		this.tail = null;
	}
	
	@Override
	public T get(T target) {
		this.access++;
		if (this.isEmpty()) {
			return null;	
		} else {
			DLLNode<T> targetNode = this.find(target);
			if (targetNode != null) { // Make this into a retrieval of a node
				this.hits++;
				this.moveToFront(targetNode);
				return target;
			} else {
				return null;
			}
		}
	}

	@Override
	public void clear() {		
		this.head = null;
		this.hits = 0;
		this.access = 0;
		this.cacheSize = 0;
	}

	@Override
	public void add(T data) {
		if (!(this.cacheSize < this.MAX_SIZE)) {
			this.removeLast();
		}
		DLLNode<T> tmpNode = new DLLNode<T>(data);
		tmpNode.setNext(this.head);		
		if (this.isEmpty()) { 
			this.tail = tmpNode;
		} else {
			this.head.setPrevious(tmpNode);
			tmpNode.setNext(this.head);
		} 		
		this.head = tmpNode;
		this.cacheSize++;
	}

	@Override
	public void removeLast() {
		if (this.isEmpty()) {
			throw new IllegalStateException();
		}
		this.removeNode(this.tail);
	}

	@Override
	public void remove(T target) {
		DLLNode<T> targetNode = this.find(target);
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		this.removeNode(targetNode);
	}

	@Override
	public void write(T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHitRate() { // PHIL TODO - Ask professor about his versus the write-up
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMissRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return (this.cacheSize == 0);
	}
	
	/**
	 * Searches the cache for target and returns node if found else returns null
	 * @param target - object of type T  
	 * @return DLLNode that contains target if found else null
	 */
	private DLLNode<T> find (T target) {
		DLLNode<T> currentNode = this.head;
		while (currentNode.getNext() != null && !currentNode.getElement().equals(target)) {
			currentNode = currentNode.getNext();
		}
		if (currentNode.getElement().equals(target)) {						
			return currentNode;
		} else {
			return null;
		}		
	}
	
	private void moveToFront(DLLNode<T> theNode) {
		if (theNode != this.head) {
			if (theNode == this.tail) {
				this.tail = theNode.getPrevious();
				this.tail.setNext(null);
			} else {
				theNode.getPrevious().setNext(theNode.getNext());
				theNode.getNext().setPrevious(theNode.getPrevious());
			}
			theNode.setNext(this.head);
			theNode.setPrevious(null);
			this.head = theNode;
		}
	}
	
	private void removeNode(DLLNode<T> theNode) {
		
	}
}
