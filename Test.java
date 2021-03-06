import java.io.*;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
/**
 * 
 * @author Phillip Sebastian
 *
 */
public class Test {

	private static final String FIRST_PARAMETER_ERROR = "The first commandline arguments needs to be a 1 or 2.";
	private static final String CACHE_SIZE_NOINT_ERROR = "The cache needs to be a valid integer which is also greater than 0.";
	private static final String CACHE2_SIZE_ERROR = "The size of cache 2 needs to be greater than the size of cache 1.";
	private static final String COMMANDLINE_INSTRUCTIONS = "This program requires the following startup parameters.\nFor one cache: 1 <cache size as Int> <input filename>For two caches: 2 <cache size as Int of 1st cache> <cache size as Int of 2nd cache and needs to be larger than cache 1> <input filename>";
	private static final String FILE_NOT_FOUND = "Unable to locate or read file: ";
	
	private boolean secondCache;
	private Cache<String> firstLevelCache;
	private Cache<String> secondLevelCache;
	private DecimalFormat dFormat;
	
	/**
	 * Constructor for creating a Test object with one cache for string objects
	 * @param firstLevelCacheSize - number of objects stored in cache
	 */
	private Test (int firstLevelCacheSize) {
		this.firstLevelCache = new Cache<String> (firstLevelCacheSize);
		this.secondLevelCache = null;
		this.secondCache = false;
		this.dFormat = new DecimalFormat("0.00%");
	}
	
	/**
	 * Constructor for creating a Test object with two caches for string objects
	 * @param firstLevelCacheSize - number of objects stored in L1 cache
	 * @param secondLevelCacheSize - number of objects stored in L2 cache
	 */
	private Test (int firstLevelCacheSize, int secondLevelCacheSize) {
		this.firstLevelCache = new Cache<String> (firstLevelCacheSize);
		this.secondLevelCache = new Cache<String> (secondLevelCacheSize);
		this.secondCache = true;
		this.dFormat = new DecimalFormat("0.00%");
	}
	
	/**
	 * Method for the Test object to search the cache(s) for string s
	 * @param s - String object to search in cache
	 */
	private void searchCaches (String s) {
		if (this.firstLevelCache.get(s) == null) {
			this.firstLevelCache.add(s);
			if (this.secondCache) { // Only check second level if it should exist
				if (this.secondLevelCache.get(s) == null) {
					this.secondLevelCache.add(s);
				}
			}			
		} else {
			if (this.secondCache) {
				if (this.secondLevelCache.get(s) == null) { // This should never occur
					this.secondLevelCache.add(s);
				}
			}
		}
	}
	/**
	 * Method overrides toString to provide desired output.
	 * @return - formatted string
	 */
	@Override
	public String toString() { 
		int adjustedSecondHits = 0;
		double tmpResult = 0.0;
		StringBuilder returnString = new StringBuilder();
		returnString.append("\nL1");
		returnString.append(" cache with " + this.firstLevelCache.getMaxSize());
		returnString.append(" entries created.");		
		if (this.secondCache) {
			returnString.append("\nL2");
			returnString.append(" cache with " + this.secondLevelCache.getMaxSize());
			returnString.append(" entries created.");
			}		
		returnString.append("\n. . .");
		returnString.append("\nNumber of L1 hits: " + this.firstLevelCache.getHits());
		tmpResult = this.firstLevelCache.getHitRate();
		returnString.append("\nL1 Hit rate: " + this.dFormat.format(tmpResult)); 
		returnString.append("\n");
		if (this.secondCache) {
			adjustedSecondHits = this.secondLevelCache.getHits() - this.firstLevelCache.getHits(); 
			returnString.append("\nNumber of L1 hits: " + adjustedSecondHits); 
			returnString.append("\nL2 Hit rate: "); 
			int adjustedSecondAccess = this.secondLevelCache.getAccess() - this.firstLevelCache.getHits();
			tmpResult = ((double) adjustedSecondHits / (double) adjustedSecondAccess);
			returnString.append(this.dFormat.format(tmpResult)); 			
			returnString.append("\n");
		}
		returnString.append("\nTotal number of accesses: " + this.firstLevelCache.getAccess());
		returnString.append("\nTotal number of hits: ");
		
		int totalHits = this.firstLevelCache.getHits();
		if (this.secondCache) {
			totalHits += adjustedSecondHits;
		}
		
		returnString.append(totalHits);
		tmpResult = this.secondLevelCache.getHitRate();
		returnString.append("\nOverall hit rate: " + this.dFormat.format(tmpResult)); 
		return returnString.toString();
	}
	
	/**
	 * Main method of java class, used for running the application
	 * @param args - command line arguments needed. # of caches, size of each cache specified individually, file to review
	 */
	public static void main (String[] args) {
		String fileName = null;
		Test thisTest = null;
		int arg0 = -1, arg1 = -1, arg2 = -1;
		
		if (args.length < 3 || args.length >  4) { 
			System.err.println(COMMANDLINE_INSTRUCTIONS);
			System.exit(1);
		} 
		
		try {
			arg0 = Integer.parseInt(args[0]); 
		} catch (NumberFormatException e) {
			System.err.println(FIRST_PARAMETER_ERROR);
			System.exit(1);
		}
		
		if (arg0 < 1 || arg0 > 2) {
			System.err.println(FIRST_PARAMETER_ERROR);
			System.exit(1);
		} 
		
		switch (arg0) {
			case 1:
				if (args.length != 3) {
					System.err.println(COMMANDLINE_INSTRUCTIONS);
					System.exit(1);
				} 
				
				try {
					arg1 = Integer.parseInt(args[1]); 
				} catch (NumberFormatException e) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				}
				
				if (arg1 < 0) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				} 
				
				fileName = args[2]; 
				thisTest = new Test(arg1);	
				break;
			case 2:
				if (args.length != 4) {
					System.err.println(COMMANDLINE_INSTRUCTIONS);
					System.exit(1);
				}
				
				try {
					arg1 = Integer.parseInt(args[1]); 
				} catch (NumberFormatException e) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				}
				
				try {
					arg2 = Integer.parseInt(args[2]); 
				} catch (NumberFormatException e) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				}
				
				if (arg1 < 0) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				} 
				if (arg2 < 0) {
					System.err.println(CACHE_SIZE_NOINT_ERROR);
					System.exit(1);
				} 
				if (arg2 < arg1) {
					System.err.println(CACHE2_SIZE_ERROR);
					System.exit(1);
				} 
				fileName = args[3]; 
				thisTest = new Test(arg1, arg2);
				break;
			default:
				System.err.println(COMMANDLINE_INSTRUCTIONS);
				System.exit(1);
				break;
		}	
		
		thisTest.processFile(fileName);
		
	}
	
	/**
	 * Method used by Test objects to review file and printout results to the console. 
	 * @param fileName - Name of file to review
	 */
	public void processFile(String fileName) {
		
		BufferedReader reader = null;
		String line;
		StringTokenizer stringLine;
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException err) {
			System.err.println(FILE_NOT_FOUND + fileName);
			System.exit(1);
		}
		
		try {
			line = reader.readLine();
			
			while (line != null) {
				stringLine = new StringTokenizer(line);
				
				while (stringLine.hasMoreTokens()) {
					this.searchCaches(stringLine.nextToken());
				}
				
				line = reader.readLine();
			}
			
		} catch (IOException err) {
			System.err.println(err);
			System.exit(1);
		}
		System.out.println(this.toString());
	}
}
