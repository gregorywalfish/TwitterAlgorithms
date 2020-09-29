package FinalProject;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class StressTestKeys extends StressTest{
    //in-house hashtable
    private MyHashTable<String, Tweet> tweetTable;
    //hash table java
    //
    private Long refExecutionTime;
    private int multiplier;

    private Set<String> authorSet;
    StressTestKeys(Integer timeOut, int mult, PrintStream output, PrintStream error){
        super(timeOut, output, error);
        this.multiplier = mult;
    }

    public void createHashTable(){
    /*Creates 2 hash tables using Java's inbuilt hash table, and
    inhouse hash table class using the data provided.
     */
        int bucketCount = (int)(data.size()/0.75) + 1;
        //sbucketCount /= 4; //trigger rehash
        tweetTable = new MyHashTable<String,Tweet>(bucketCount);
        ArrayList<String> authorList = new ArrayList<String>();
        for (Tweet tweet: data) {
            tweetTable.put(tweet.getAuthor(), tweet);
            authorList.add(tweet.getAuthor());
        }
        this.authorSet = new HashSet<String>(authorList);
    }
    /*
     *  Provide implementation of this (tester) method  for each test.
     */

    private void getReferenceExecutionTime()
    {
        this.refExecutionTime = (long)80;
        this.out.print("Approximate execution time (for reference) " + this.refExecutionTime+"ms");
    }

    Boolean tester(){
        try {
            getReferenceExecutionTime();
            Long startTime = System.currentTimeMillis();

            ArrayList<String> keyList = tweetTable.keys();

            Long timeTaken = System.currentTimeMillis() - startTime;

            if (!Thread.interrupted()) {
                if (verbose) {
                    if (keyList.size() == this.authorSet.size()) {
                        this.out.println(" | Execution time of solution code : " + timeTaken + "ms");

                        if (timeTaken < this.multiplier * this.refExecutionTime) {
                            this.out.println("[PASS] Code executed under acceptable time.");
                            return true;
                        } else
                            this.out.println("[FAIL] Code is not optimized enough.");
                    } else {
                        this.out.println("[FAIL] The number of keys returned does not match with the size of the original dataset!");
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
