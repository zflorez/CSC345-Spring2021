/*
 * Author: Zachary Florez
 * Course: CSC 345
 * Class: Deque
 * Description: This class holds a Deque object thats manually created through a 
 * 				array. This array is generic so it can store any kind of object
 * 				and it is dynamically resizable. 
 * 				
 */

@SuppressWarnings("unchecked")
public class Deque<Item> {

	private Item[] deque;
	public int accessCount; // Keeps count of how many arrayAccesses. 
	public int size; // Keeps count of how many elements in Deque
	public int front; // Keeps a pointer to the front of the Deque
	public int back; // Keeps a pointer to the back of the Deque. 

	/***
     *constructor: create an empty Deque with initial
     *capacity of 10
     */
	public Deque() {
		this.front = 0;
		this.back = 0; 
		deque = (Item[]) new Object[10];
		this.size = 0;

	}

	/***
     *constructor: create an empty Deque with initial
     *capacity of n
     */
	public Deque(int n) {
		this.front = 0;
		this.back = 0; 
		deque = (Item[]) new Object[n];
		this.size = 0;
	}

	/**
	 * adds item to the front of the deque, if the deque is full, then it will
	 * resize it by doubling the size of the array.
	 * 
	 * @param item, item that's being added to front of deque.
	 */
	public void addToFront(Item item) {
		
		if (this.front == this.back && deque[front] == null) {
			// Array is empty
			deque[front] = item;
			accessCount ++;
			this.back ++;
			if (this.back == deque.length) {
				this.back = 0;
			}

		} else if (this.front == this.back && deque[front] != null) {
			// Array is full
			Item[] tempArray = (Item[]) new Object[this.size * 2];
			tempArray[0] = item;
			
			// Loop through and store front values to a tempArray 
			// And then store values back into Deque. 
			for (int i = 0; i < deque.length; i ++) {
				tempArray[i + 1] = deque[front];
				this.front ++;
				if (this.front == deque.length) {
					this.front = 0; 
				}
			}
			
			accessCount += deque.length + 1;
			this.back = deque.length + 1;
			deque = tempArray; 
			this.front = 0;
			
			// Update size. 
			this.size ++;
			
		} else if (this.front == 0 && deque[front] != null) {
			// front is at index zero and has to wrap around. 
			this.front = deque.length - 1;
			deque[front] = item;
			accessCount ++;
		} else if (this.front == 0 && this.back == 0) {
			// start of empty Deque. 
			deque[front] = item; 
			this.back ++;
		} else {
			this.front --;
			deque[front] = item;
			accessCount ++;
		}
		
		this.size ++;
	}

	/**
	 * adds item to the back of the deque, if the deque is full, then it will resize
	 * it by doubling the size of the array.
	 * 
	 * @param item, item that's being added to back of deque.
	 */
	public void addToBack(Item item) {
		if(this.back == this.front && deque[front] == null) {
			// Deque is empty
			deque[front] = item;
			this.back ++;
			accessCount ++;
		} else if (this.back == this.front && deque[front] != null) {
			
			// Deque is full
			Item[] tempArray = (Item[]) new Object[this.size * 2];
			
			// Loop through entire deque and store values into a temp 
			// value that will be stored back into the new deque. 
			for (int i = 0; i < deque.length; i ++) {
				tempArray[i] = deque[front];
				this.front ++;
				if (this.front == deque.length) {
					this.front = 0; 
				}
			}
			
			this.back = deque.length;
			accessCount += deque.length + 1;
			deque = tempArray;
			deque[back] = item;
			this.back ++;
			this.front = 0;
			
			// Increase size. 
			this.size ++;
			
		} else if (this.back == deque.length && deque[back - 1] != null) {
			
			// back is at last index and needs to wrap around. 
			this.back = 0; 
			deque[back] = item; 
			this.back ++; 
			accessCount ++;
		} else {
			deque[back] = item; 
			this.back ++; 
			accessCount ++;
		}
		
		if (this.back == deque.length) {
			this.back = 0;
		}
		
		// Increase size. 
		this.size ++;
	} 

	/**
	 * removes and returns the Item that's at the front of the deque. If removing
	 * the Item makes the size fall below 1/4 capacity, then we cut the array size
	 * in half.
	 * 
	 * @return Item, the first item at the front of the deque, if the deque is
	 *         empty, Exception is thrown.
	 * @throws Exception
	 */
	public Item getFirst() throws EmptyDequeException {
		
		accessCount ++;
		
		if (this.front == deque.length) {
			this.front = 0;
		} if (deque[front] == null) {
			// Check if the deque is empty.
			throw new EmptyDequeException();
		}
		
		Item retVal = deque[front];
		deque[front] = null;
		this.front ++;
		return retVal;
		
	}

	/**
	 * removes and returns the Item that's at the back of the deque. If removing the
	 * Item makes the size fall below 1/4 capacity, then we cut the array size in
	 * half.
	 * 
	 * @return Item, the last item at the back of the deque, if the deque is empty,
	 *         Exception is thrown.
	 */
	public Item getLast() throws EmptyDequeException {
		
		accessCount ++;
		
		if (this.back == -1 || this.back == 0) {
			// Make sure we don't run into IndexOutOfBounds errors. 
			this.back = deque.length;
		} else if (deque[back - 1] == null) {
			// Check if the deque is empty. 
			throw new EmptyDequeException();
		}  
		
		Item retVal = deque[back - 1];
		deque[back - 1] = null;
		this.back --;
		
		return retVal;
	}
	
	/**
	 * Finds and returns the Item at the front of the dequq. If the deque is empty,
	 * then Exception is thrown.
	 * 
	 * @return Item, located at the front of the deque.
	 */
	public Item peekFirst() throws EmptyDequeException{
		
		accessCount ++;
		
		if (deque[front] == null) {
			throw new EmptyDequeException();
		}
		return deque[front];
	}

	/**
	 * Finds and returns the Item at the back of the deque. If the deque is empty,
	 * then Exception is thrown.
	 * 
	 * @return Item, located at the back of the deque.
	 */
	public Item peekLast() throws EmptyDequeException{
		
		accessCount ++;
		
		// First we check to see if deque is empty
		if (deque[back - 1] == null) {
			throw new EmptyDequeException();
		} 
		return deque[back - 1];
	} 

	/**
	 * @return true if deque is empty, false other wise.
	 * 
	 */
	public boolean isEmpty() {
		if (this.front == this.back && deque[front] == null) {
			return true;
		}
		return false;
	}

	/**
	 * @return integer elements in your deque.
	 */
	public int size() {
		return size;
	}

	/**
	 * @return the array access count of deque.
	 */
	public int getAccessCount() {
		return accessCount;
	}

	/**
	 * resets the array access count back to 0;
	 */
	public void resetAcessCount() {
		this.accessCount = 0;
	}
	
	/***
     *return the underlying array
     ***/
    public Item[] getArray() {
		return deque;
    }

	public static void main(String[] args) throws Exception {
		
		// Calculate runtime for addToFront/addToBack opertations.
		// Initalize our command line args. 
		int N = Integer.parseInt(args[0]);
		int capacity = Integer.parseInt(args[1]);
		
		System.out.printf("N = %d and Capacity = %d\n", N, capacity);
		
		// create deque. 
		Deque<Integer> deque = new Deque<Integer>(capacity);
		
		// Now we set our array access to zero before we get started. 
		// And then perform N amount of addToFront operations. 
		deque.accessCount = 0;
		for (int i = 0; i < N; i ++) {
			deque.addToFront(i);
		}
		System.out.printf("The number of array accesses for %d amount of addToFront = %d\n", N, deque.accessCount);
		
		deque.resetAcessCount();
		for (int i = 0; i < N; i ++) {
			deque.getFirst();
		}
		System.out.printf("The number of array accesses for %d amount of getFirst = %d\n", N, deque.accessCount);
		
		
		// Then we reset the array access to zero before our next step. 
		// And now we calculate the array access to remove all the 
		// elements that were in our deque already. 
		Deque<Integer> deque2 = new Deque<Integer>(capacity);

		for (int i = 0; i < N; i ++) {
			deque2.addToBack(i);
		}
		System.out.printf("The number of array accesses for %d amount of addToBack = %d\n", N, deque2.accessCount);
		
		deque2.resetAcessCount();
		for (int i = 0; i < N; i ++) {
			deque2.getLast();
		}
		System.out.printf("The number of array accesses for %d amount of getLast = %d\n", N, deque.accessCount);
	}
}
