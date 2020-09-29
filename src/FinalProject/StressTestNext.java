package FinalProject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;


public class StressTestNext extends StressTestBase {
	
	MyHashTable<String,Tweet> myHashTable;
	Iterator myHashIterator;
	
	StressTestNext(Integer timeOut) {
		super(timeOut);
	}
	
	StressTestNext(Integer timeOut , PrintStream outputStream, PrintStream errorStream)
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
		
		myHashIterator = this.myHashTable.iterator();
	}
	

	@Override
	Boolean tester() {
		try {
			while (myHashIterator.hasNext())
			{
				myHashIterator.next();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace(this.err);
			return false;
		}
	}

}
