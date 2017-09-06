import java.util.NoSuchElementException;

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
	
	/**
	 * Constructor to create a cache with a maximum number of elements as defined by the maxSize parameter
	 * @param maxSize - Integer indicating the maximum size of the cache
	 */
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
		if (this.isEmpty()) {
			this.access++;
			return null;	
		} else {
			T retData = this.accessData(target);
			if (retData != null) {
				this.hits++;
			}
			return retData;
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
		} else {
		this.removeNode(this.tail);
		}	
	}

	@Override
	public void remove(T target) {
		DLLNode<T> targetNode = this.find(target);
		if (targetNode == null) {
			throw new NoSuchElementException();
		} else {
			this.removeNode(targetNode);
		}		
	}

	@Override
	public void write(T data) {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			T result = this.accessData(data);
			if (result == null) {
				throw new NoSuchElementException();
			} else {
				this.hits++;
			}	
		}		
	}

	@Override
	public double getHitRate() { // PHIL TODO - Ask professor about his versus the write-up
		if (this.access > 0) {
			return ((double) this.hits / (double) this.access);
		} else {
			return 0;
		}

	}

	@Override
	public double getMissRate() {
		return (1 - this.getHitRate());
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
	
	/**
	 * Private method to search for T target, if found move to front and return target else returns null
	 * @param target - object of type T
	 * @return returns T target if found else returns null
	 */
	private T accessData(T target) {
		this.access++;
		DLLNode<T> targetNode = this.find(target);
		if (targetNode != null) { // Make this into a retrieval of a node
			this.hits++;
			this.moveToFront(targetNode);
			return target;
		} else {
			return null;
		}
		
	}
	
	/**
	 * Moves indicated node to the front of the cache
	 * @param theNode - DLLNode of object T
	 */
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

	/**
	 * 
	 * @param theNode
	 */
	private void removeNode(DLLNode<T> theNode) {
		// PHIL TODO - WORK ON THIS
		
		this.cacheSize--;
	}
	
	/**
	 * 
	 * @param cacheLevel
	 * @return
	 */
	public String toStringCreation(String cacheLevel) { // PHIL TODO -- ask about this and other new public methods
		StringBuilder retStr = new StringBuilder();
		retStr.append("\t"+ cacheLevel);
		retStr.append(" cach with " + this.MAX_SIZE);
		retStr.append(" created.\n");
		return retStr.toString();
	}
	
	/**
	 * 
	 * @param cacheLevel
	 * @return
	 */
	public String toStringHits(String cacheLevel) {
		StringBuilder retStr = new StringBuilder();
		retStr.append("\tNumber of " + cacheLevel);
		retStr.append(" hits: " + this.hits);
		retStr.append("\n" + cacheLevel + " Hit rate: " + this.getHitRate());
		return retStr.toString();
	}
}
