/**
 * 
 */

/**
 * @author Phillip Sebastian
 *
 */
public class Cache<T> implements ICache<T> {
	private int cacheCount;
	private int MAX_SIZE;
	private int hits;
	private int access;
	
	public Cache(int maxSize){
		this.MAX_SIZE = maxSize;
		this.cacheCount = 0;
		this.hits = 0;
		this.access = 0;
	}
	
	@Override
	public T get(T target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(T target) {
		// TODO Auto-generated method stub
		
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

}
