
/**
 * @author Zachary Florez
 *
 * Class that holds Priority Queue backed by a Array. 
 */

public class PQueue {
		
	Loc[] Q; 
	int length;
	int lastElementIndex;
	
	/**
	 * Constructor. 
	 * @param size, integer.
	 */
	public PQueue(int size) {
		Q = new Loc[size];
		lastElementIndex = 0;
	}
	
	/**
	 * Inserts a new Loc into the P Queue, also Enqueue()
	 * 
	 * @param newLoc, Loc
	 */
	public void insert(Loc newLoc) {
		
		// Next check so we don't get index out of bounds. 
		// Resize Queue if needed. 
		if (lastElementIndex + 1 > Q.length - 1) {
			Loc[] tempQueue = Q;
			Q = new Loc[tempQueue.length * 2];
			
			for (int i = 0; i < tempQueue.length; i ++) {
				Q[i] = tempQueue[i];
			}
		}
		
		// Now call swim to swim the inserted item up
		// if needed be. 
		lastElementIndex ++;
		Q[lastElementIndex] = newLoc;
		length ++;
		swim();
	}
		
	/**
	 * Function that is called from insert to swim a 
	 * Location upwards until it is not 
	 * less than it's parent. 
	 */
	private void swim() {
		int currIndex = lastElementIndex; 
		int parentIndex = getParentIndex(currIndex);
		
		// Now we can go through and keep swimming up 
		// until we get to where we want to be. 
		while (parentIndex > 0 && Q[currIndex].distance < Q[parentIndex].distance) {
			swapElements(currIndex, parentIndex);
			currIndex = parentIndex;
			parentIndex = getParentIndex(currIndex);
		}	
	}
	
		
	/**
	 * Function called when we want to swap two elements in 
	 * our priority queue. 
	 * @param element1, int index 
	 * @param element2, int index
	 */
	private void swapElements(int element1, int element2) {
		Loc temp = Q[element1];
		Q[element1] = Q[element2];
		Q[element2] = temp;
	}
		
		
	/**
	 * Helper function called when we have to swim up to get the 
	 * index of a locations parent. 
	 * 
	 * @param childIndex, int of child location. 
	 * @return int, index of parent Node. 
	 */
	private int getParentIndex(int childIndex) {
		
		if (childIndex == 0) return -1;
		
		if (childIndex == 1 || childIndex == 2) return 0; 
			
		else return (childIndex / 2);
	}
		
	/** 
	 * Helper function called to get a left child in a 
	 * min-heap backed by an array. 
	 *
	 * @param index, int
	 * @return int, index of parents left child. 
	 */
	private int getLeftChild(int index) {
		return (index * 2) <= lastElementIndex ? (index * 2) : 0;
	}
		
	/**
	 * Helper function called to get a right child in a 
	 * min-heap backed by an array. 
	 *
	 * @param index, int
	 * @return int, index of parents right child. 
	 */
	private int getRightChild(int index) {
		return (index * 2 + 1) <= lastElementIndex ? (index * 2 + 1) : 0;
	}
		
		
	/**
	 * Helper function called after a Loc is deleted 
	 * from the queue to sink down a Loc that was swapped
	 * to the root (index 0) to delete the minimum value. 
	 *
	 */	
	private void sink() {
		
		// Swapping the root (that just got swapped after 
		// delete min all the way down to place the smallest 
		// value at the root of the queue. 
		int index = 1; 
			
		while (index < lastElementIndex) {
			int minLocValue = Q[index].distance;
			int minIndex    = index; 
				
			// Check Left First 
			int left = getLeftChild(index);
			if (left > 0 && minLocValue > Q[left].distance) {
				minLocValue = Q[left].distance;
				minIndex    = left;
			}
			
			// Check Right Next
			int right = getRightChild(index);
			if (right > 0 && minLocValue > Q[right].distance) {
				minLocValue = Q[right].distance;
				minIndex    = right;
			}
			
			if (minIndex == index) {
				break;
			}
			
			swapElements(minIndex, index);
			index = minIndex;	
		}
			
	}
		
	/**
	 * Deletes the min Location object in the PQueue 
	 * and then returns it.
	 * @return Loc, minimum Loc in Queue
	 */
	public Loc deleteMin() {
		
		// First check to make sure that the back of the queue pointer 
		// is not less than zero so we don't get an exception. 
		if (lastElementIndex <= 0) {
			System.out.println("Back of Queue is less than zero!!!!! ");
			return null;
		}
		
		Loc headOfQueue = Q[1];
		swapElements(1, lastElementIndex);
		lastElementIndex --; 
		length --;
		sink();
			
		return headOfQueue;
	}
	
	public String toString() {
		String queue = "{ ";
		
		for (Loc elements : Q) {
		if (elements != null) {
				if (elements.getIntVal() < 10) {
					queue += elements.distance + "   ";
				} else 
					queue += elements.distance + "  ";
			}
		}
		
		queue += "}";
		
		return queue;
	}
}
