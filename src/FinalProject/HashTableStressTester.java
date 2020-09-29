package FinalProject;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;


/**
 * Class for testing your implementation of the HashTable class.
 */
public class HashTableStressTester{
    
    /**
     * Returns a list of songs to use for testing the hash table.
     * @return A list of songs to use for testing the hash table
     */
    private static String tweetsFileName;
    private static String stopWordsFileName;
    private static ArrayList<Tweet> tweets;
    private static ArrayList<String> stopWords;
    private static String outputfile = "mini_stress_feedback_solution.txt";

 private static PrintStream output;
 private static PrintStream err;

    public static void main(String[] args) throws IOException{

     output = new PrintStream(System.out); // new File (outputfile)
     err = new PrintStream(System.err);
  
     System.out.println("Working Directory = " +System.getProperty("user.dir"));
  
  // get file names for stress test
  if (args.length > 0) {
   tweetsFileName = args[0];
  } else {
   tweetsFileName = "tester_tweets.csv";
  }

  if (args.length > 1) {
   stopWordsFileName = args[1];
  } else {
   stopWordsFileName = "stopWords.txt"; 
  }

  readStressTestFiles(tweetsFileName, stopWordsFileName);

  int multiplier=3;
  testPut(1500, multiplier);
  testGet(1500, multiplier);
  testRemove(1500, multiplier);
  testKeys(1500, multiplier);
        testValues(1500, multiplier);

        testFastSort(1500, multiplier);
        testMyHashIterator(60);
        testNext(30);
  output.println("**This tester mostly " +
    "concerns itself with the efficient implementation of the methods.**");
  System.exit(0);
    }
 
 public static void readStressTestFiles(String tweetsFileName, String stopWordsFileName) {
  
  DateTimeFormatter dateFormatFile = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss xx yyyy");
  DateTimeFormatter dateFormatTweets = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  BufferedReader brTweets = null;
  BufferedReader brStopwords = null;
  String line = "";
  String cvsSplitBy = "\t";

  tweets = new ArrayList<Tweet>();
  stopWords = new ArrayList<String>();

  try {

   // read tweets into Arraylist

   brTweets = new BufferedReader(new FileReader(tweetsFileName));
   brTweets.readLine(); // skip first line
   while ((line = brTweets.readLine()) != null) {

    // use comma as separator
    String[] tweetArray = line.split(cvsSplitBy);
    
    // skip first line
    
    // skip multi line tweets in quotes
    if (tweetArray.length < 3)
    {
     continue;
    }
    
    if (tweetArray[2].contains("\""))
    {
     continue;
    }
    
    TemporalAccessor dateTime =  dateFormatFile.parse(tweetArray[1]);
    
    Tweet tweet = new Tweet(tweetArray[0], dateFormatTweets.format(dateTime), tweetArray[2]);

    tweets.add(tweet);
   }

   // read stopWords into ArrayList

   brStopwords = new BufferedReader(new FileReader(stopWordsFileName));
   while ((line = brStopwords.readLine()) != null) {

    // use comma as separator

    stopWords.add(line);
   }

  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   if (brTweets != null) {
    try {
     brTweets.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
   if (brStopwords != null) {
    try {
     brStopwords.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  }
 }

    private static void testPut(Integer timeOut, int mult){
 output.println("----------------Put Stress Test------------");
 StressTestPut putTest = new StressTestPut(timeOut, mult, output, err);
 putTest.setData(tweets);
 putTest.createHashTable();
 Boolean successFlag = putTest.run();

 return;
    }

    private static void testGet(Integer timeOut, int mult){
 output.println("----------------Get Stress Test------------");
 StressTestGet getTest = new StressTestGet(timeOut, mult, output, err);
 getTest.setData(tweets);
 getTest.createHashTable();
 Boolean successFlag = getTest.run();

  return;
    }

 private static void testRemove(Integer timeOut, int mult){
  output.println("----------------Remove Stress Test------------");
  StressTestRemove removeTest = new StressTestRemove(timeOut, mult, output, err);
  removeTest.setData(tweets);
  removeTest.createHashTable();
        Boolean successFlag = removeTest.run();
  return;
 }
/*
    private static void testRehash(Integer timeOut, int mult){
  output.println("----------------Rehash Stress Test------------");
        StressTestRehash rehashTest = new StressTestRehash(timeOut, mult, output, err);
        rehashTest.setData(tweets);
        rehashTest.createHashTable();
        Boolean successFlag = rehashTest.run();
        return;
    }

 */
 private static void testKeys(Integer timeOut, int mult) {
        output.println("----------------Keys Stress Test------------");
        StressTestKeys keysTest = new StressTestKeys(timeOut, mult, output, err);
        keysTest.setData(tweets);
        keysTest.createHashTable();
        Boolean successFlag = keysTest.run();
        return;
    }

 private static void testValues(Integer timeOut, int mult){
  output.println("----------------Values Stress Test------------");
  StressTestValues valuesTest = new StressTestValues(timeOut, mult, output, err);
  valuesTest.setData(tweets);
  valuesTest.createHashTable();
        Boolean successFlag = valuesTest.run();
  return;
 }

 private static void testFastSort(Integer timeOut, int mult){
  output.println("----------------FastSort Stress Test------------");
  TestFastSort fastSortTest = new TestFastSort(timeOut, mult, output, err);
  fastSortTest.setData(tweets);
  fastSortTest.createHashTable();
        Boolean successFlag = fastSortTest.run();
  return;
 }
 
 private static void testMyHashIterator(Integer timeOut) {
  output.println("----------------MyHashIterator() constructor----------------");
  StressTestMyHashIterator test = new StressTestMyHashIterator(timeOut, output, err);
  test.setData(tweets, stopWords);
  Long testTime = test.run();
  output.println("Approximate execution time (for reference) " + timeOut 
    + "ms | Execution time of solution code : " + testTime + "ms");
  if (testTime < timeOut)
  {
   output.println("[PASS] Code executed correctly under acceptable time.");
  }
  else
  {
   output.println("[FAIL] Code is not optimized enough.");
  }
  return;
 }
 
 private static void testNext(Integer timeOut) {
  output.println("----------------Next()----------------");
  StressTestNext test = new StressTestNext(timeOut, output, err);
  test.setData(tweets, stopWords);
  Long testTime = test.run();
  output.println("Approximate execution time (for reference) " + timeOut 
    + "ms | Execution time of solution code : " + testTime + "ms");
  if (testTime < timeOut)
  {
   output.println("[PASS] Code executed correctly under acceptable time.");
  }
  else
  {
   output.println("[FAIL] Code is not optimized enough.");
  }
  return;
 }

}
