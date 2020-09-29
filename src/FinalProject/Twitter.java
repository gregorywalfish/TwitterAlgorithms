package FinalProject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Twitter {

	// ADD YOUR CODE BELOW HERE
	private MyHashTable<String, Tweet> tweetAuthorTable;
	private MyHashTable<String, ArrayList<Tweet>> tweetDateTable;

	private ArrayList<Tweet> tweets;
	private ArrayList<String> stopWords;

	// ADD CODE ABOVE HERE

	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		// ADD YOUR CODE BELOW HERE
		this.tweetAuthorTable = new MyHashTable<String, Tweet>(10);
		this.tweetDateTable = new MyHashTable<String, ArrayList<Tweet>>(10);
		this.stopWords =stopWords;
		this.tweets = tweets;
		this.stopWords = stopWords;
		for (Tweet t : tweets) {
			addTweet(t);

		}

		// ADD CODE ABOVE HERE
	}

	/**
	 * Add Tweet t to this Twitter O(1)
	 */
	public void addTweet(Tweet t) {
		// ADD CODE BELOW HERE

		tweetAuthorTable.put(t.getAuthor(), t); // latest tweet will be added to the twitter latest and replace

		ArrayList<Tweet> tweetsOnDate = tweetDateTable.get(t.getDateAndTime().substring(0, 10));

		if (tweetsOnDate == null) {
			ArrayList<Tweet> dateTweets = new ArrayList<Tweet>();
			tweetsOnDate = dateTweets;

		}
		tweetsOnDate.add(t);

		tweetDateTable.put(t.getDateAndTime().substring(0, 10), tweetsOnDate);

	}

	/**
	 * Search this Twitter for the latest Tweet of a given author. If there are no
	 * tweets from the given author, then the method returns null. O(1)
	 */
	public Tweet latestTweetByAuthor(String author) {
		// ADD CODE BELOW HERE

		return tweetAuthorTable.get(author);

		// ADD CODE ABOVE HERE
	}

	/**
	 * Search this Twitter for Tweets by `date' and return an ArrayList of all such
	 * Tweets. If there are no tweets on the given date, then the method returns
	 * null. O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {
		// ADD CODE BELOW HERE

		return tweetDateTable.get(date);

		// ADD CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of words (that are not stop words!) that appear in the
	 * tweets. The words should be ordered from most frequent to least frequent by
	 * counting in how many tweet messages the words appear. Note that if a word
	 * appears more than once in the same tweet, it should be counted only once.
	 */
	public ArrayList<String> trendingTopics() {
		// ADD CODE BELOW HERE
		MyHashTable<String, Integer> wordsTable = new MyHashTable<String, Integer>(10);
		boolean isStopWord = false;
		boolean repeatedWord = false;
		int tweeters = 0;
		for (Tweet t : tweets) {

			ArrayList<String> wordsInTweet = new ArrayList<String>();

			for (String word : getWords(t.getMessage())) {
				// we check if it is a stop word
				for (String stopWord : stopWords) {
					if (word.equalsIgnoreCase(stopWord)) {

						isStopWord = true;
					}

				}

				repeatedWord = alreadyAdded(wordsInTweet, word);
				wordsInTweet.add(word);

				if (!isStopWord && !repeatedWord) {
					// check if this word already exists as a key
					// if yes, we will update the value of how many times it occurs
					for (LinkedList<HashPair<String, Integer>> list : wordsTable.getBuckets()) {
						for (HashPair<String, Integer> pair : list) {
							if (pair.getKey().equalsIgnoreCase(word)) {
								tweeters = pair.getValue();
							}
						}
					}
					// if not, we make a new hashpair

					tweeters++;
					wordsTable.put(word, tweeters);
					tweeters = 0;

				}
				isStopWord = false;
				repeatedWord = false;
			}
		}

		// now we sort and extract words
		ArrayList<String> trendingTopics = MyHashTable.fastSort(wordsTable);
		return trendingTopics;
	}
//	public ArrayList<String> trendingTopics() {
//		// ADD CODE BELOW HERE
//		MyHashTable<String, Integer> wordsTable = new MyHashTable<String, Integer>(10);
//		boolean isStopWord = false;
//		boolean repeatedWord = false;
//		int tweeters = 0;
//		for (Tweet t : tweets) {
//			ArrayList<String> wordsInTweet = new ArrayList<String>();
//			for (String word : getWords(t.getMessage())) {
//				wordsInTweet.add(word);
//				// we check if it is a stop word
//				for (String stopWord : stopWords) {
//					if (word.equalsIgnoreCase(stopWord)) {
//
//						isStopWord = true;
//					}
//
//				}
//
//				repeatedWord = alreadyAdded(wordsInTweet, word);
//
//				if (!repeatedWord) {
//					// check if this word already exists as a key
//					// if yes, we will update the value of how many times it occurs
//					for (LinkedList<HashPair<String, Integer>> list : wordsTable.getBuckets()) {
//						for (HashPair<String, Integer> pair : list) {
//							if (pair.getKey().equalsIgnoreCase(word)) {
//								tweeters = pair.getValue();
//							}
//						}
//					}
//					// if not, we make a new hashpair
//
//					tweeters++;
//					wordsTable.put(word, tweeters);
//					tweeters = 0;
//
//				}
//			}
//		}
//
//		// now we sort and extract words
//		ArrayList<String> trendingTopics = MyHashTable.fastSort(wordsTable);
//		return trendingTopics;
//
//	}

	private boolean alreadyAdded(ArrayList<String> wordsInTweet, String currentWord) {
		for (String word : wordsInTweet) {
			if (word.equalsIgnoreCase(currentWord)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * An helper method you can use to obtain an ArrayList of words from a String,
	 * separating them based on apostrophes and space characters. All character that
	 * are not letters from the English alphabet are ignored.
	 */
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			String w = "";
			for (int j = 0; j < words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;

			}
			wordsList.add(w);
		}
		return wordsList;
	}

}
