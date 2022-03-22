/*
 * Author: Zachary Florez
 * Course: CSC 345
 * Class:  LocSort
 * Description: In this class we implement different sorting methods such as selection sort, 
 * 				insertion sort, heap sort, and merge sort understanding that there is a 
 * 				special value d such that the range of the sorting is determined by already
 * 				before the sorting even begins. 
 */

public class LocSort {
    
    /***
     *locality-aware selection sort on array a
     *with locality-aware value d
     ***/
    public static void selectionSort(int[] a, int d) {
    	
    	// if we know that every input array a has the property such that
    	// any element doesn't move more than d places away from it's original position,
    	// Then we easily make the runtime O(dN) for this algorithm. 
    	
    	int min_element, length;
    	
    	length = a.length;
    	
    	// Start of main for-loop through entire array. 
    	// O(N) OPERATION
    	for (int i = 0; i < length - 1; i ++) {
    		
    		// First we set our limits and min element
    		min_element = i;
    		int max = length - 1;
    		int min = i + 1;
    		
    		// Set our lower limit based off of d
    		if (min_element - d > min) {
    			min = min_element - d;
    		}
    		
    		// Set our upper limit based off of d
    		if (min_element + d < max) {
    			max = min_element + d;
    		}
    		
    		// Next for-loop to find the minimum element in unsorted array. 
    		// O(d) OPERATION
    		for (int j = min; j < max + 1; j ++) {
    			if (a[j] < a[min_element]) {
    				min_element = j;
    			}
    		}
    		
    		// Next we swap the two elements
    		int temp = a[min_element];
    		a[min_element] = a[i];
    		a[i] = temp;
    	}
    }

    /***
     *locality-aware insertion sort on array a
     *with locality-aware value d
     ***/
    public static void insertionSort(int[] a, int d) {
    	// if we know that every input array a has the property such that
    	// any element doesn't move more than d places away from it's original position,
    	// Then we easily make the runtime O(dN) for this algorithm. 
    	
    	// First we create a pointer that is current element we're working with. 
    	int element, j; 
    	
    	// Loop through entire array
    	// O(N) OPERATION
    	for (int i = 1; i < a.length; i ++) {
    		element = a[i];
    		j = i - 1;
    		
    		int min = 0; 
    		
    		// Set minimum for O(d) operation
    		if (i - d > 0) {
    			min = i - d;
    		}
    		
    		// loop only while j is greater than or equal to minimum 
    		// and while a[j] is greater than the element we're currently on. 
    		// O(d) OPERATION
    		while (j >= min && a[j] > element) {
    			// Swap 1
    			a[j + 1] = a[j];
    			j = j - 1;
    		}
    		// Swap 2
    		a[j + 1] = element;
    	} // END FOR
    }

    /***
     *locality-aware heapsort on array a
     *with locality-aware value d.
     ***/
    public static void heapSort(int[] a, int d) {
    	
    	// First we get our size, here we have to check to make sure 
    	// d isn't greater then the size of the array or else it isn't 
    	// really locality awareness. 
    	int size;
    	if (d < a.length) {
    		size = d + 1; 
    	} else {
    		size = a.length;
    	}
    	// Create new heap
    	int[] newHeap = new int[size];
    	
    	// Loop to store values in range of d into newHeap
    	for (int i = 0; (i < size && i < a.length); i ++) {
    		newHeap[i] = a[i];
    	}
    	
    	// build a new heap
    	createHeap(newHeap, size);
    	
    	// Now we loop through in range of d to delete items and then 
    	// insert new items into original array
    	for (int i = size; i < a.length; i ++) {
    		a[i - size] = delete(newHeap, size);
    		insert(newHeap, size, a[i]);
    	}
    	
    	int value = size; 
    	for (int i = a.length; i-size < a.length; i ++) {
    		a[i - size] = delete(newHeap, value);
    		value --;
    	}
    } 
    
    /**
     * function that is called from heapSort function. Creates a simple 
     * heap array by calling heapify function. 
     * 
     * @param heap, int array
     * @param size, int 
     */
    public static void createHeap(int[] heap, int size) {
    	
    	for (int i = size - 1; i >=0; i --) {
    		heapify(heap, size, i);
    	}
    }
    
    /**
     * function that is called from heapSort function. No return 
     * 
     * @param heap, int array
     * @param size, int
     * @param element1, int 
     */
    public static void insert(int[] heap, int size, int element1) {
    	
    	// First declare our variables and store size - 1 into heap
    	int element2 = size - 1;
    	heap[element2]  = element1;
    	int i = element2;
    	
    	// Now we loop through and insert element. 
    	while (heap[(i-1) / 2] > heap[i] && i > 0) {
    		int temporary = heap[i];
    		heap[i] = heap[(i-1) / 2];
    		heap[(i-1) / 2] = temporary; 
    		i = (i-1) / 2;
    	}
    }
    
    /**
     * function that is called from heapSort function. returns an integer. 
     * 
     * @param heap, int array
     * @param size, int
     * @return integer 
     */
    public static int delete(int[] heap, int size) {
    	int pointer = heap[0];
    	heap[0]     = heap[size - 1];
    	heapify(heap, size - 1, 0);
    	
    	return pointer;
    }
    
    /**
     * function that is called from most heapSort smaller functions. 
     * Heapifies the array a. No return
     * 
     * @param a, int array
     * @param size, integer
     * @param i, integer
     */
    public static void heapify(int[]a, int size, int i) {
    	int smallest   = i;
    	int leftChild  = 2 * i + 1;
    	int rightChild = 2 * i + 2;
    	
    	// Swap leftChild and parent
    	if (leftChild < size && a[leftChild] < a[smallest]) {
    		smallest = leftChild;
    	}
    	
    	// swap rightChild and parent
    	if (rightChild < size && a[rightChild] < a[smallest]) {
    		smallest = rightChild;
    	}
    	
    	// Jump in here if smallest element got changed above ^
    	if (smallest != i) {
    		a[smallest] = a[smallest] - a[i];
    		a[i] = a[smallest] + a[i];
    		a[smallest] = a[i] - a[smallest];
    		heapify(a, size, smallest);
    	}
    }
    
    /**
     * creates a middle pointer from the array and then merges the two. 
     * @param a, int[] array
     * @param temp, int[] array
     * @param i, integer
     * @param j, integer
     */
    public static void mergeSortAux(int[] a, int[] temp, int i, int j) {
    	if (j - i < 1) {
    		return;
    	}
    	
    	int middle = (i + j) / 2;
    	mergeSortAux(a, temp, i, middle);
    	mergeSortAux(a, temp, middle + 1, j);
    	merge(a, temp, i, middle, middle + 1, j);
    }
    
    /**
     * Sorts int[] arr into temp and then puts all the values back in 
     * at the end of the function. 
     * 
     * @param arr, int[] array
     * @param temp, int[] array
     * @param start, integer
     * @param middle, integer
     * @param middlePlus1, integer
     * @param end, integer
     */
    public static void merge(int[] arr, int[] temp, int start, int middle, int middlePlus1, int end) {
    	
    	int k = start;
    	int c = start;
    	
    	while ((start <= middle || middlePlus1 <= end) && (middlePlus1 < arr.length) && (start < arr.length)) {
    		
    		if (start > middle) {
    			temp[c] = arr[middlePlus1];
    			c++;
    			middlePlus1++;
    		} else if (middlePlus1 > end) {
    			temp[c] = arr[start];
    			c++;
    			start++;
    		} else if (arr[start] <= arr[middlePlus1]) {
    				temp[c] = arr[start];
        			c++;
        			start++;
    		} else {
    			temp[c] = arr[middlePlus1];
    			c++;
    			middlePlus1++;
    		}
    	}
    	
    	// Now put all the elements back into the array. 
    	for (int i = k; i <= end; i ++) {
    		arr[i] = temp[i];
    	}
    }

    /***
     *locality-aware mergeSort on array a 
     *with locality-aware value d
     ***/
    public static void mergeSort(int[] a, int d) {
    	
    	// First we have to check to make sure our locality 
    	// aware integer isn't bigger than our array size.
    	if (d >= a.length) {
    		int[] temp = new int[a.length];
    		mergeSortAux(a, temp, 0, a.length - 1);
    	} else {
    		int[] temp = new int[a.length];
    		mergeSortAux(a, temp, 0, 2*d);
    	}
    }
}

