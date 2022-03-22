/*
 * Author: Zachary Florez
 * Course: CSC 345
 * Class: Hashtable
 * Description: This class implements a Hashtable with String values that are being 
 * 				Hashed. This Hashtable is also implemented with quadratic probing. 
 */

public class Hashtable {
	
	static String[]  keys;
	static Integer[] values;
	private int size; 
	
	/*
	 * Basic Constructor for a HashTable
	 */
	public Hashtable() {
		
		
		// Set reasonable starting capacity for a 
		// new hashtable of prime number. 
		keys = new String[7];
		values = new Integer[7];
		size = 0;
	}
	
	/*
	 * Hashtable constructor for a desired size to start with 
	 * m.
	 */
	public Hashtable(int m) {
		keys = new String[m];
		values = new Integer[m];
		size = 0;
	}
	
	/*
	 * Put a key value into the table according to quadratic 
	 * probing. Also check the load factor AFTER a element 
	 * is put into the HashTable. 
	 */
	public void put(String key, Integer val) {
		
		boolean put = putHelper(key, val, keys, values);
		
		// Only increment size if we added a new key. 
		if (put)
			size ++;
		
		// Store load factor 
		double loadFactor = (double) size / keys.length;
		
		// resize to larger array if loadFactor greater than 1/2
		if (loadFactor > .5) {
			
			size = 0;
			int newSize = nextPrimeNumber(keys.length * 2);
			String[] tempKeys = new String[newSize];
			Integer[] tempVals = new Integer[newSize];
			
			// Loop through all elements in current key array 
			// and hash them to the new key array. 
			for (int i = 0; i < keys.length; i ++) {
				if (keys[i] == null) {
					continue;
				} else {
					boolean works = putHelper(keys[i], values[i], tempKeys, tempVals);
					if (works)
						size ++;
				}
			}
			// Restore new arrays. 
			keys   = tempKeys;
			values = tempVals;
		}
	}
	
	/*
	 * Helper function that is called when we want to figure out where exactly 
	 * to put a desired key and value inside our two arrays. In this function 
	 * we get the hash function of the key and continue to do quadratic probing
	 * until we find a right spot for the key to be placed in. 
	 * 
	 * Return true if the key and value were able to be put in 
	 * Return false otherwise. 
	 */
	public static boolean putHelper(String key, int val, String[] keyArray, Integer[] valArray) {
		
		// First thing we do is get the hash value of the key. 
		int hashVal = (((key.hashCode() % keyArray.length) + keyArray.length) % keyArray.length);
		int i = hashVal;
		
		// In order to find if we have put the key into the hashTable 
		// we set a boolean and turn that boolean to true once we find a
		// spot for the key/value 
		
		boolean put = false; 
		int quadraticNumber = 1;
		while (put == false) {
			
			// First check to make sure index isn't out of bounds. 
			if (i >= keyArray.length)
				i -= keyArray.length;
			
			// if the element is already taken and it isn't 
			// with the same key. 
			if (keyArray[i] != null && key != keyArray[i]) {
				i = hashVal + (((quadraticNumber) * quadraticNumber) % keyArray.length);
				quadraticNumber ++; 
				continue;
			
			// Next we check if the element we're checking has the same key
			// as the one we're adding. 
			} else if (key == keyArray[i]) {
				valArray[i] = val;
				put = true;
				
			// Other wise we add new element and increase size and set put to true
			// so the loop ends. 
			} else {
				keyArray[i] = key;
				valArray[i] = val;
				put = true;
				return true; 
			}
		} // END WHILE
		return false;
	}
	
	/*
	 * Calculates the next prime number either 
	 * when we want to increase of decrease the size of the 
	 * array and returns that next prime number. 
	 */
	public static int nextPrimeNumber(int n) {
		
		int prime = n;
		
		// First check base case
		if (n <= 1) return 2;
		
		// Now we can loop through until we find the next 
		// prime number after n. 
		boolean found = false; 
		while (found == false) {
			prime ++; 
			if (isPrime(prime))
				found = true;
		}
		
		return prime;
	}
	
	/*
	 * Helper function to find if a number is prime number when we have to increase
	 * the size of the hash table
	 */
	public static boolean isPrime(int n) {
		
		// Base Case
		if (n <= 1) return false;
		if (n <= 3) return true; 
		
		// Checked in order to skip middle five numbers. 
		if (n % 2 == 0 || n % 3 == 0) return false;
		
		for (int i = 5; i * i <= n; i = i + 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;
		
		// Otherwise n has to be true!
		return true;
	}
	
	/*
	 * Retrieves the Integer value that is paired with 
	 * the given key, other wise null is returned if the key is not 
	 * in the table. 
	 */
	public Integer get(String key) {
		
		// Loop through key array to see if parameter 
		// key is inside the key array and return value 
		// at that index if it is. 
		for (int i = 0; i < keys.length; i ++) {
			
			if (keys[i] == null)
				continue;
			
			if (keys[i].equals(key)) 
				return values[i];
		}
		return null;
		
	}
	
	/*
	 * Deletes the item from the table that is paired with
	 * the passed key and returns the value associated 
	 * with the said key or null if the key is not 
	 * inside the table. 
	 */
	public Integer delete(String key) {
		
		// Loop through all the elements in the key array 
		// to see if parameter key is inside the key array. 
		for (int i = 0; i < keys.length; i ++) {
			
			if (keys[i] == null)
				continue;
			
			// If the param key equals a key inside the key array 
			// then turn both those values to null and decrease size by 1. 
			if (keys[i].equals(key)) {
				int value = values[i];
				keys[i]   = null;
				values[i] = null;
				size --; 
				
				// Now we have to check the load factor. 
				double loadFactor = (double) size / keys.length;
				
				if (loadFactor < .125) {
					fixArray();
				}
				return value;
			}
		} // END FOR
		return null; 
	}
	
	/*
	 * Helper function that is called from delete function 
	 * if the load factor was less than 1/8. 
	 * 
	 * Inside this function we just create two new temp arrays 
	 * of the new prime size and loop through the elements inside 
	 * the current keys and values and then call the helper function 
	 * putHelper that places the keys and values in the old array into
	 * the new one and then restores the old arrays. 
	 */
	public static void fixArray() {
		
		int newSize = nextPrimeNumber(keys.length / 3);
		
		String[] tempKeys  = new String[newSize];
		Integer[] tempVals = new Integer[newSize];
		
		// Loop through all elements in current key array 
		// and hash them to the new key array. 
		for (int i = 0; i < keys.length; i ++) {
			if (keys[i] == null) {
				continue;
			} else {
				putHelper(keys[i], values[i], tempKeys, tempVals);
			}
		}
		
		// Restore old arrays. 
		keys   = tempKeys;
		values = tempVals;
	}
	
	/*
	 * Returns true if the table contains the given key and 
	 * false otherwise. 
	 */
	public boolean contains(String key) {
		
		for (String element : keys) {
			if (element == null)
				continue;
			if (element.equals(key))
				return true; 
		}
		return false;
	}
	
	/*
	 * Returns true if the table is empty and 
	 * false otherwise. 
	 */
	public boolean isEmpty() {
		if (size == 0)
			return true; 
		return false; 
	}
	
	/*
	 * Returns the current size of the table. 
	 */
	public int size() {
		return size;
	}
}



