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
			if (this.find(target, false)) {
				this.hits++;
				return target;
			} else {
				return null;
			}
		}
		// PHIL TODO - does this need to return the founded item in the cache?
	}

	@Override
	public void clear() {		
		this.head = null;
		this.hits = 0;
		this.access = 0;
		this.cacheSize = 0;
		// PHIL TODO - does this clear the rest of the list? should it?
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
		// PHIL TODO - Check boundaries! What about adding to 0 size, 1 size, 2 size, n-size??		
	}

	@Override
	public void removeLast() {
		if (this.isEmpty()) {
			throw new IllegalStateException();
		}
		this.tail.getPrevious().setNext(null);
		this.tail = this.tail.getPrevious();
		this.cacheSize--;
		// PHIL TODO - check this that it is correct
		
	}

	@Override
	public void remove(T target) {
		if (!this.find(target, true)) {
			throw new NoSuchElementException();
		}
		// PHIL TODO - check this		
	}

	@Override
	public void write(T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHitRate() {
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
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * Searches the cache for target and if indicated to remove when called, will remove from list
	 * @param target - object of type T  
	 * @param remove - indicates whether to remove target T from list if found
	 */
	private boolean find (T target, boolean remove) {
		// PHIL TODO - write this algorithm to search and if to remove the remove first T target; return false if not found
		// look at element!!
		return false;
	}
}
