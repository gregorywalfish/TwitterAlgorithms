package FinalProject;

import java.io.PrintStream;
import java.util.ArrayList;


public class StressTestMyHashIterator extends StressTestBase {
	
	MyHashTable<String,Tweet> myHashTable;

	StressTestMyHashIterator(Integer timeOut) {
		super(timeOut);
	}
	
	StressTestMyHashIterator(Integer timeOut , PrintStream outputStream, PrintStream errorStream)
	{
		super(timeOut, outputStream, errorStream);
	}
	
	@Override
	void setData(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		this.tweets = tweets;
		this.stopWords = stopWords;
		
		// load hash table manually
		this.myHashTable = new MyHashTable<>(tweets.size() * 2);
		for (Tweet t : tweets) {
			this.myHashTable.put(t.getAuthor(),t);
		}
	}

	@Override
	Boolean tester() {
		try {
			this.myHashTable.iterator();
			return true;
		} catch (Exception e) {
			e.printStackTrace(this.err);
			return false;
		}
	}

}
