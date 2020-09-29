package FinalProject;

import java.util.ArrayList;

/**
 * Class for testing your implementation of the HashTable class.
 */
public class HashTableTester {

	/**
	 * Returns a list of tweets to use for testing the hash table.
	 * 
	 * @return A list of tweets to use for testing the hash table
	 */
	private static ArrayList<Tweet> initTweetList() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(new Tweet("USER_989b85bb", "2010-03-04 15:34:46",
				"@USER_6921e61d I can be made into one twitter superstar."));
		tweets.add(new Tweet("USER_a75657c2", "2010-03-03 00:02:54",
				"@USER_13e8a102 They reached a compromise just on time"));
		tweets.add(new Tweet("USER_989b85bb", "2010-03-04 15:34:47", "I can be MADE into a need."));
		tweets.add(new Tweet("USER_a75657c2", "2010-03-07 21:45:48",
				"So SunChips made a bag that is 100% biodegradeable. It is about damn time somebody did."));
		tweets.add(new Tweet("USER_ee551c6c", "2010-03-07 15:40:27",
				"drthema: Do something today that feeds your spirit and empowers you to start the week from a higher place."));
		tweets.add(new Tweet("USER_6c78461b", "2010-03-03 05:13:34",
				"@USER_a3d59856 yes, i watched that foolery done disturbed my spirit. @USER_b1d28f26"));
		tweets.add(new Tweet("USER_92b2293c", "2010-03-04 14:00:11",
				"@USER_5aac9e88: Let no one push u around today! Be at Peace! If u dont have restful spirit, u'll definitely have a stressful spirit"));
		tweets.add(new Tweet("USER_75c62ed9", "2010-03-07 03:35:38",
				"@USER_cb237f7f Congrats on everything I am there in spirit my brother."));
		tweets.add(new Tweet("USER_7f72a368", "2010-03-07 07:18:22",
				"Actions speak louder than words but feelings and spirits speak louder than anything #FACT"));
		tweets.add(new Tweet("USER_b6cc1831", "2010-03-07 04:04:37", "@USER_be777094 urban spirit cafe. On Long st"));
		tweets.add(new Tweet("USER_65006b55", "2010-03-05 00:58:28",
				"RT @USER_86e8d97f: @USER_65006b55's spirit just took a turn for the worst. Lol please."));
		tweets.add(new Tweet("USER_60b9991b", "2010-03-04 22:33:23",
				"Who on my time ever flew on spirit airlines let me kno if there decent"));
		tweets.add(new Tweet("USER_36607a99", "2010-03-03 02:06:01",
				"@USER_561fe280: Nourish your spirit with your own achievement."));
		tweets.add(new Tweet("USER_9506fb5f", "2010-03-04 01:16:34",
				"Great spirits have often encountered violent opposition from weak minds"));
		tweets.add(new Tweet("USER_d3ca457f", "2010-03-03 04:53:06",
				"RT @USER_6d6bfb4d: The things that make a woman beautiful are her character, intellect, and spirituality."));
		tweets.add(new Tweet("USER_14f78255", "2010-03-03 17:07:45",
				"@USER_9afbc367 Oh in spirit. That's all that matters lol"));
		tweets.add(new Tweet("USER_3dfae4fe", "2010-03-05 00:44:33",
				"time for a spiritual cleansing of my facebook friend list"));
		tweets.add(new Tweet("USER_bd852fb7", "2010-03-03 14:19:51",
				"RT @USER_24bd1961:God's spirit is like a Radio station, broadcasting all the time. You just have to learn how to tune in and receive his signal"));
		tweets.add(new Tweet("USER_136c16da", "2010-03-07 19:56:54",
				"RT @USER_11d35e61: @USER_136c16da finally a kindred spirit. *daps* lol thanks"));
		tweets.add(new Tweet("USER_47063e51", "2010-03-04 12:47:54",
				"cathartic - noun - a purification or purgation that brings about spiritual renewal or release from tension"));
		tweets.add(new Tweet("USER_1e4eb302", "2010-03-03 20:13:18",
				"Anything worth having you have to contribute yourself heart, mind, soul and spirit to. It is so rewarding. Have u contributed lately?"));
		tweets.add(new Tweet("USER_5d246e83", "2010-03-04 14:57:01",
				"@USER_8e090edb That's always good to hear. Starting off to a good morning, always puts your spirit in a great place."));
		tweets.add(new Tweet("USER_b7117680", "2010-03-03 06:55:17", "I got a hustlas spirit, period!"));
		tweets.add(new Tweet("USER_25ecff25", "2010-03-05 17:33:20",
				"RT @USER_3a117437: The woman at the rental car spot tried 2 give us a Toyota! No ma'am lk the old spiritual says \"aint got time 2 die!\""));
		tweets.add(new Tweet("USER_f91d8165", "2010-03-03 22:33:24",
				"#RandomThought why do people grab guns or knives when they think theres a ghost? DUMBASS! You can't shoot a spirit, grab some holy water! duh"));
		tweets.add(new Tweet("USER_86c542b8", "2010-03-04 02:52:06",
				"@USER_8cd1512d haha, maybe your right. I use to watch gymnastics all the time. I love the olympics. That's why I have so much spirit lol"));

		return tweets;
	}

	public static void main(String[] args) {
		ArrayList<Tweet> tweets = initTweetList();
		MyHashTable<String, Tweet> tweetTable;
		int numBuckets = 7;
		// Initialize the hash table. Key will be the tweet dateAndTime.

		tweetTable = new MyHashTable<String, Tweet>(numBuckets);
		for (Tweet t : tweets) {
			tweetTable.put(t.getDateAndTime(), t);
		}

		System.out.println("New MyHashtable created.....");
		System.out.println("Number of tweets in the table: " + tweetTable.size());
		System.out.println("Number of buckets in the table: " + tweetTable.numBuckets());
		// System.out.println(tweetTable);

		System.out.println("\n---------------\nTesting get:\n---------------\n");
		// Try to retrieve a tweet
		StringBuffer errors = new StringBuffer();
		Tweet testTweet0 = tweetTable.get("2010-03-04 15:34:47");
		System.out.println("testTweet0: \t" + testTweet0);
		if (testTweet0 == null || !testTweet0.getAuthor().equals("USER_989b85bb")
				|| !testTweet0.getMessage().equals("I can be MADE into a need.")) {
			errors.append("Failed to retrieve tweet from '2010-03-04 15:34:47'.\n");
		}

		System.out.println("\n---------------\nTesting rehash:\n---------------\n");
		// rehashing changes the capacity of the table, but not the number of entries
		Integer oldBucketCount = tweetTable.numBuckets();
		Integer oldSize = tweetTable.size();
		tweetTable.rehash();
		Integer newBucketCount = tweetTable.numBuckets();
		if (2 * oldBucketCount != newBucketCount || oldSize != tweetTable.size()) {
			errors.append("New bucket count = " + newBucketCount + "\n");
			errors.append("Expected bucket count = " + 2 * oldBucketCount + "\n");
			errors.append("New size = " + tweetTable.size() + "\n");
			errors.append("Expected size = " + oldSize + "\n");
		}

		// Try to retrieve a tweet
		Tweet testTweet1 = tweetTable.get("2010-03-04 15:34:47");
		System.out.println("testTweet1: \t" + testTweet1);
		if (testTweet1 == null || !testTweet1.getAuthor().equals("USER_989b85bb")
				|| !testTweet1.getMessage().equals("I can be MADE into a need.")) {
			errors.append("Failed to retrieve tweet from '2010-03-04 15:34:47'.\n");
		}

		System.out.println("\n---------------\nTesting remove:\n---------------\n");
		// Try to remove a tweet
		Tweet removedTweet = tweetTable.remove("2010-03-03 06:55:17");
		Tweet retrievedTweet = tweetTable.get("2010-03-03 06:55:17");
		System.out.println("removedTweet: \t" + removedTweet);
		System.out.println("retrivedTweet: \t" + retrievedTweet);
		if (removedTweet == null || !removedTweet.getAuthor().equals("USER_b7117680")
				|| !removedTweet.getMessage().equals("I got a hustlas spirit, period!") || retrievedTweet != null) {
			errors.append("Failed to remove tweet from '2010-03-03 06:55:17'.\n");
		}

		// ***************** Twitter Basic Checks *****************//
		System.out.println("\n*********************\nTwitter Basic Checks");

		ArrayList<String> stopWords = new ArrayList<String>();
		Twitter t = new Twitter(initTweetList(), stopWords);

		System.out.println("\n---------------\nTesting latestTweetByAuthor:\n---------------\n");
		Tweet tweetByAuthor = t.latestTweetByAuthor("USER_989b85bb");
		if (tweetByAuthor == null || !tweetByAuthor.getAuthor().equals("USER_989b85bb")
				|| !tweetByAuthor.getMessage().equals("I can be MADE into a need.")
				|| !tweetByAuthor.getDateAndTime().equals("2010-03-04 15:34:47")) {
			errors.append("Failer to retrieve the latest tweet by USER_989b85bb\n");
		}
		System.out.println("Latest tweet by USER_989b85bb :" + tweetByAuthor);

		System.out.println("\n---------------\nTesting tweetByDate:\n---------------\n");
		ArrayList<Tweet> tweetsByDate = t.tweetsByDate("2010-03-03");
		if (tweetsByDate.size() != 9) {
			errors.append("Failed to retrieve all 9 tweets posted on 2010-03-03\n");
		}

		System.out.println(tweetsByDate.size() + " tweets posted on 2010-03-03 :");
		tweetsByDate.forEach(tweet -> System.out.println("\t" + tweet));

		// PUT MORE TESTS HERE.
		System.out.println("\n---------------\nTesting Trending Topics:\n---------------\n");
		ArrayList<String> trendingWords = t.trendingTopics();
		if (trendingWords.get(0).equalsIgnoreCase("spirit") && trendingWords.get(1).equalsIgnoreCase("a")) {
			
			System.out.println(trendingWords.get(0));
			System.out.println("Trending topics : passed");
		}

		// Display the test results
		System.out.println("\n---------------\nTEST RESULTS:\n---------------\n");
		if (errors.length() == 0) {
			errors.append("All tests passed successfully.");
		}
		System.out.println(errors.toString());
		System.out.println();

	}

}
