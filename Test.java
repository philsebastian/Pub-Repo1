import java.io.*;
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
	
	private int firstCacheHits;
	private int secondCacheHits;
	private int firstCacheReferences;
	private int totalCacheReferences;
	private boolean secondCache;
	private Cache<String> firstLevelCache;
	private Cache<String> secondLevelCache;
	
	/**
	 * 
	 * @param firstLevelCacheSize
	 */
	private Test (int firstLevelCacheSize) {
		this.firstLevelCache = new Cache<String> (firstLevelCacheSize);
		this.secondLevelCache = null;
		this.secondCache = false;
		this.firstCacheHits = 0;
		this.secondCacheHits = 0;
		this.firstCacheReferences = 0;
		this.totalCacheReferences = 0;		
	}
	
	/**
	 * 
	 * @param firstLevelCacheSize
	 * @param secondLevelCacheSize
	 */
	private Test (int firstLevelCacheSize, int secondLevelCacheSize) {
		this.firstLevelCache = new Cache<String> (firstLevelCacheSize);
		this.secondLevelCache = new Cache<String> (secondLevelCacheSize);
		this.secondCache = true;
		this.firstCacheHits = 0;
		this.secondCacheHits = 0;
		this.firstCacheReferences = 0;
		this.totalCacheReferences = 0;
	}
	
	/**
	 * 
	 * @param s
	 */
	private void addToCaches(String s) {
		this.firstLevelCache.add(s);
		if (this.secondCache) {
			this.secondLevelCache.add(s);
		}
	}
	
	/**
	 * 
	 * @param s
	 */
	private void searchCaches (String s) {
		this.totalCacheReferences++;
		String retString = this.firstLevelCache.get(s);
		if (retString != null) {
			this.firstCacheHits++;
			this.firstCacheReferences++;
			this.secondLevelCache.get(s);
		} else {
			if (this.secondCache) {
				retString = this.secondLevelCache.get(s);
				if (retString != null) {
					this.secondCacheHits++;
					this.firstLevelCache.add(s);
				}
			}
		}
		if (retString == null) {
			this.addToCaches(s);
		}
	}
	
	@Override
	public String toString () { 
		StringBuilder returnString = new StringBuilder();
		int totalCacheHits;
		double globalRatio, level1Ratio, level2Ratio;
		
		returnString.append("First level cache with ");		
		// TODO - returnString.append(firstLevelCache.getMaxSize());
		returnString.append(" entries has been created.");
		returnString.append("\n");		
		if (secondCache) {
			returnString.append("Second level cache with ");		
			// TODO - returnString.append(secondLevelCache.getMaxSize());
			returnString.append(" entries has been created.");
			returnString.append("\n");		
		}
		returnString.append("..............................");		
		returnString.append("\n");
		
		returnString.append("Total number of references:");		
		returnString.append("\t\t");		
		returnString.append(this.totalCacheReferences);		
		returnString.append("\n");		
		returnString.append("Total number of cache hits:");		
		returnString.append("\t\t");
		
		if (secondCache) {
			totalCacheHits = this.firstCacheHits + this.secondCacheHits;
		} else {
			totalCacheHits = this.firstCacheHits;
		}
		returnString.append(totalCacheHits);
		returnString.append("\n");
		
		returnString.append("The global hit ratio:");		
		returnString.append("\t\t\t");		
		globalRatio = (double) totalCacheHits / (double) this.totalCacheReferences;
		returnString.append(globalRatio);		
		returnString.append("\n");	
		
		returnString.append("Number of 1st-level cache hits:");		
		returnString.append("\t\t");
		returnString.append(this.firstCacheHits);
		returnString.append("\n");	
		returnString.append("1st-level cache hit ratio:");		
		returnString.append("\t\t");
		level1Ratio = (double) this.firstCacheHits / (double) this.totalCacheReferences;
		returnString.append(level1Ratio);		
		returnString.append("\n");	
		
		if (secondCache) {
			returnString.append("Number of 2nd-level cache hits:");		
			returnString.append("\t\t");
			returnString.append(this.secondCacheHits);
			returnString.append("\n");	
			returnString.append("2nd-level cache hit ratio:");		
			returnString.append("\t\t");
			level2Ratio = (double) this.secondCacheHits / (double) (this.totalCacheReferences - this.firstCacheReferences);
			returnString.append(level2Ratio);		
			returnString.append("\n");	
		}
		
		return returnString.toString();
	}
	
	/**
	 * 
	 * @param args
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
	 * 
	 * @param fileName
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
