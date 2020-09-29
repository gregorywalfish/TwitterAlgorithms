package FinalProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
	// num of entries to the table
	private int numEntries;
	// num of buckets
	private int numBuckets;
	// load factor needed to check for rehashing
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<HashPair<K, V>>> buckets;

	// constructor
	public MyHashTable(int initialCapacity) {
		// ADD YOUR CODE BELOW THIS
		this.buckets = new ArrayList<LinkedList<HashPair<K, V>>>();
		this.numBuckets = initialCapacity;
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new LinkedList<HashPair<K, V>>());
		}

	}

	public int size() {
		return this.numEntries;
	}

	public boolean isEmpty() {
		return this.numEntries == 0;
	}

	public int numBuckets() {
		return this.numBuckets;
	}

	/**
	 * Returns the buckets variable. Useful for testing purposes.
	 */
	public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode()) % this.numBuckets;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair to this
	 * HashTable. Expected average run time O(1)
	 */
	public V put(K key, V value) {
		// ADD YOUR CODE BELOW HERE
		int position = hashFunction(key);
		HashPair<K, V> pairFound = keyExists(key, position);
		boolean keyExists = false;

		if (pairFound != null) {
			keyExists = true;
		}
		// If the key already exists in the hash table,
		// Overwrite the old value with the new one.
		// returns old value
		if (keyExists) {
			V oldVal = pairFound.getValue();
			pairFound.setValue(value);
			return oldVal;
		} else {
			if (getLoadFactor() >= MAX_LOAD_FACTOR) {
				rehash();
			}
			LinkedList<HashPair<K, V>> list = buckets.get(position);
			HashPair<K, V> freshPair = new HashPair<K, V>(key, value);
			list.add(freshPair);
			numEntries++;
		}
		// If there was no value associated to the given key,
		// add a HashPair of the key and value to the hash table,
		// return null

		return null;

	}

	private double getLoadFactor() {
		double loadFactor = (double) numEntries / numBuckets;
		return loadFactor;
	}

	// return true if the key already exists, and false if not
	// we check each hashpair in the linkedlist
	private HashPair<K, V> keyExists(K key, int position) {
		LinkedList<HashPair<K, V>> lList = buckets.get(position);
		for (HashPair<K, V> pair : lList) {
			if (pair.getKey().equals(key)) {
				return pair;
			}
		}
		return null;
	}

	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		int position = hashFunction(key);
		HashPair<K, V> pairFound = keyExists(key, position);
		boolean keyExists = false;

		if (pairFound != null) {
			keyExists = true;
		}

		if (keyExists) {
			return pairFound.getValue();
		}

		return null;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1)
	 */
	public V remove(K key) {
		int position = hashFunction(key);
		LinkedList<HashPair<K, V>> lList = buckets.get(position);
		HashPair<K, V> pairFound = keyExists(key, position);
		boolean keyExists = false;
		if (pairFound != null) {
			keyExists = true;
		}

		if (keyExists) {
			V value = pairFound.getValue();
			lList.remove(pairFound);
			numEntries--;
			return value;
		}
		return null;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Method to double the size of the hashtable if load factor increases beyond
	 * MAX_LOAD_FACTOR. Made public for ease of testing. Expected average runtime is
	 * O(m), where m is the number of buckets
	 */
	public void rehash() {
		// ADD YOUR CODE BELOW HERE
		MyHashTable<K, V> hashT = new MyHashTable<K, V>(numBuckets * 2);

		for (LinkedList<HashPair<K, V>> lists : buckets) {
			for (HashPair<K, V> pair : lists) {
				hashT.put(pair.getKey(), pair.getValue());
			}

		}
		this.buckets = hashT.buckets;
		this.numBuckets = hashT.numBuckets;
		this.numEntries = hashT.numEntries;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Return a list of all the keys present in this hashtable. Expected average
	 * runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> keys() {
		ArrayList<K> list = new ArrayList<K>(numEntries);
		for (LinkedList<HashPair<K, V>> bucket : buckets) {
			for (HashPair<K, V> pair : bucket) {
				list.add(pair.getKey());
			}
		}
		return list;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable. Expected
	 * average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> values() {
		ArrayList<V> list = new ArrayList<V>();
		MyHashTable<V, V> valTable = new MyHashTable<V, V>(numEntries);

		for (LinkedList<HashPair<K, V>> bucket : buckets) {
			if (bucket != null) {
				for (HashPair<K, V> pair : bucket) {
					valTable.put(pair.getValue(), pair.getValue());
				}
			}
		}

		for (LinkedList<HashPair<V, V>> bucket1 : valTable.getBuckets()) {
			if (bucket1 != null) {
				for (HashPair<V, V> pair : bucket1) {
					list.add(pair.getValue());
				}
			}
		}

		return list;
		// ADD CODE ABOVE HERE
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n^2), where n is the number of pairs
	 * in the map.
	 */
	public static <K, V extends Comparable<V>> ArrayList<K> slowSort(MyHashTable<K, V> results) {
		ArrayList<K> sortedResults = new ArrayList<>();
		for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
			while (i >= 0) {
				toCompare = results.get(sortedResults.get(i));
				if (element.compareTo(toCompare) <= 0)
					break;
				i--;
			}
			sortedResults.add(i + 1, toAdd);
		}
		return sortedResults;
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number of
	 * pairs in the map.
	 */

	// we did quicksort. The form i used is as seen in the notes for Comp 250
	// quicksort just as we learned in class.

	public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
		// ADD CODE BELOW HERE
		ArrayList<HashPair<K, V>> aList = new ArrayList<HashPair<K, V>>();
		for (HashPair<K, V> pair : results) {
			aList.add(pair);

		}
		quickSort(aList, 0, aList.size() - 1);
		ArrayList<K> sortedResults = new ArrayList<K>();
		for (HashPair<K, V> aPair : aList) {
			sortedResults.add(aPair.getKey());
		}
		return sortedResults;

		// ADD CODE ABOVE HERE
	}

	private static <K, V extends Comparable<V>> void quickSort(ArrayList<HashPair<K, V>> list, int leftIndex,
			int rightIndex) {
		if (leftIndex >= rightIndex) {
			return;
		} else {
			int i = placeAndDivide(list, leftIndex, rightIndex);
			quickSort(list, leftIndex, i - 1);
			quickSort(list, i + 1, rightIndex);
		}

	}

	private static <K, V extends Comparable<V>> int placeAndDivide(ArrayList<HashPair<K, V>> list, int leftIndex,
			int rightIndex) {
		V pivot = list.get(rightIndex).getValue();
		int wall = leftIndex - 1;
		for (int i = leftIndex; i < rightIndex; i++) {
			if (list.get(i).getValue().compareTo(pivot) > 0) {
				wall++;
				V temp = list.get(i).getValue();
				list.get(i).setValue(list.get(wall).getValue());
				list.get(wall).setValue(temp);
			}
		}
		V temp1 = list.get(rightIndex).getValue();
		list.get(rightIndex).setValue(list.get(wall + 1).getValue());
		list.get(wall + 1).setValue(temp1);
		return wall + 1;

	}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}

	private class MyHashIterator implements Iterator<HashPair<K, V>> {
		// ADD YOUR CODE BELOW HERE
		LinkedList<HashPair<K, V>> aList = new LinkedList<HashPair<K, V>>();

		// ADD YOUR CODE ABOVE HERE

		/**
		 * Expected average runtime is O(m) where m is the number of buckets
		 */
		private MyHashIterator() {
			// ADD YOUR CODE BELOW HERE
			if (aList.isEmpty()) {
				for (LinkedList<HashPair<K, V>> bucket : buckets) {
					for (HashPair<K, V> pair : bucket) {
						aList.add(pair);
					}
				}
			}
			// ADD YOUR CODE ABOVE HERE
		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public boolean hasNext() {
			// ADD YOUR CODE BELOW HERE
			if (aList.isEmpty()) {
				return false;
			}
			return true;

			// ADD YOUR CODE ABOVE HERE
		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public HashPair<K, V> next() {
			// ADD YOUR CODE BELOW HERE

			return aList.pop();

			// ADD YOUR CODE ABOVE HERE
		}

	}
}
