package FinalProject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


public class StressTestGet extends StressTest {
	//in-house hashtable
    private MyHashTable<String, String> tweetTable;
    //hash table java
    private Hashtable<String, String> javaTweetTable;
    //
    private Long refExecutionTime;
    private int multiplier;

    private ArrayList<String> authorList;
    private ArrayList<String> msgList = new ArrayList<String>();


    StressTestGet(Integer timeOut, int mult, PrintStream output, PrintStream error){
    	super(timeOut, output, error);
    	this.multiplier = mult;
    }
    
    public void createHashTable(){
    /*Creates 2 hash tables using Java's inbuilt hash table, and
    inhouse hash table class using the data provided.
     */
	int bucketCount = (int)(data.size()/0.75) + 1;
	//sbucketCount /= 4; //trigger rehash
	tweetTable = new MyHashTable<String,String>(bucketCount);
	javaTweetTable = new Hashtable<String,String>(bucketCount);
	ArrayList<String> tempAuthorList = new ArrayList<String>();

	for (Tweet tweet: data)
	{
	    tweetTable.put(tweet.getAuthor(), tweet.getMessage());
	    javaTweetTable.put(tweet.getAuthor(), tweet.getMessage());
	    tempAuthorList.add(tweet.getAuthor());
	}
        Set<String> authorSet = new HashSet<String>(tempAuthorList);
        this.authorList = new ArrayList<String>(authorSet);

    }
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */

    private void getReferenceExecutionTime()
    {

        try {
            Long refStartTime = System.currentTimeMillis();

            for (String author : this.authorList) {
                this.msgList.add(javaTweetTable.get(author));
            }
            this.refExecutionTime = System.currentTimeMillis() - refStartTime;
            this.out.print("Approximate execution time (for reference) " + this.refExecutionTime+"ms");
        }catch (Exception e)
        {
            this.out.println("Failed to obtain a reference time.");
        }
    }

    Boolean tester(){
	try {
	    ArrayList<String> msgsfromTweetTable = new ArrayList<String>();
	    getReferenceExecutionTime();

		Long startTime = System.currentTimeMillis();

		for (String author : this.authorList) {
			msgsfromTweetTable.add(tweetTable.get(author));
		}
		Long timeTaken = System.currentTimeMillis() - startTime;

		if (!Thread.interrupted()) {
            if (verbose) {
                if (msgsfromTweetTable.size() == this.msgList.size()) {
                    this.out.println(" | Execution time of solution code : " + timeTaken + "ms");

                    if (timeTaken < this.multiplier * this.refExecutionTime) {
                        this.out.println("[PASS] Code executed under acceptable time.");
                        return true;
                    } else
                        this.out.println("[FAIL] Code is not optimized enough.");
                } else {
                    this.out.println("[FAIL] The size of the tables created did not match!");
                    return false;
                }
            }
        }
		return false;
	}catch(Exception e){
	    this.err.println(e);
	    return false;
	}
    }

}
