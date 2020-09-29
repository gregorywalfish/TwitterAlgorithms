package FinalProject;

import java.io.PrintStream;
import java.util.*;

public class StressTestValues extends StressTest{
    //in-house hashtable
    private MyHashTable<String, String> tweetTable;
    //hash table java
    private Hashtable<String, String> javaTweetTable;
    //
    private Long refExecutionTime;
    private int multiplier;
    private Collection<String> refValues;
    private Set<String> valueSet;

    StressTestValues(Integer timeOut, int mult, PrintStream output, PrintStream err){
        super(timeOut, output, err);
        this.multiplier = mult;
    }

    public void createHashTable(){
    /*Creates 2 hash tables using Java's inbuilt hash table, and
    inhouse hash table class using the data provided.
     */
        int bucketCount = (int)(data.size()/0.75) + 1;
        //sbucketCount /= 4; //trigger rehash
        tweetTable = new MyHashTable<String,String>(bucketCount);
        javaTweetTable = new Hashtable<String, String>(bucketCount);
        for (Tweet tweet: data) {
            tweetTable.put(tweet.getAuthor(), tweet.getMessage());
            javaTweetTable.put(tweet.getAuthor(), tweet.getMessage());
        }
    }
    /*
     *  Provide implementation of this (tester) method  for each test.
     */

    private void getReferenceExecutionTime()
    {
        try {
            this.refValues = javaTweetTable.values();
            this.refExecutionTime = (long)100;
            this.out.print("Approximate execution time (for reference) "+ this.refExecutionTime+"ms");
            ArrayList<String> refvalueList = new ArrayList<String>();

            for (String val : this.refValues)
                refvalueList.add(val);
            this.valueSet = new HashSet<String>(refvalueList);


        }catch (Exception e)
        {
            this.out.println("Failed to obtain a reference time.");
        }
    }

    Boolean tester(){
        try {
            getReferenceExecutionTime();
            Long startTime = System.currentTimeMillis();

            ArrayList<String> tweetList = tweetTable.values();

            Long timeTaken = System.currentTimeMillis() - startTime;

            if (!Thread.interrupted()) {
                if (verbose) {
                    if (tweetList.size() == this.valueSet.size()) {
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
