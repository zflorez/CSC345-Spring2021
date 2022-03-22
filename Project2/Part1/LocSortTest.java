import java.util.Arrays;
import java.util.Random;

public class LocSortTest {
    //test values
    private static int[] sizes = new int[] {10, 25, 100, 250, 1250, 5000, 10000, 50, 15000, 20000};
    private static int[] dVals = new int[] {3, 7, 15, 30, 10, 50, 10, 55, 15, 25};

    private static int singleTest = 2;
    
    public static void main(String[] args) {
	int score = 0;
	score += testSelectionSort();
	score += testInsertionSort();
	score += testHeapSort();
	score += testMergeSort();
	System.out.println("\n\nExpected Score: " + score);
    }

    private static int testSelectionSort() {
	System.out.println("\n***Testing Selection Sort***");
	int score = 0;
	for(int i = 0; i < sizes.length; i++) {
	    System.out.println("\nTesting on array of size " + sizes[i] + " with d = " + dVals[i] + "...");
	    int[] array = ArrayGen.getRand(sizes[i], dVals[i]);
	    int[] copy = copyArray(array);
	    Arrays.sort(copy);
	    LocSort.selectionSort(array, dVals[i]);
	    if(isSorted(array, copy))
		score += singleTest;
	}
	return score;
    }

    private static int testInsertionSort() {
	System.out.println("\n***Testing Insertion Sort***");
	int score = 0;
	for(int i = 0; i < sizes.length; i++) {
	    System.out.println("\nTesting on array of size " + sizes[i] + " with d = " + dVals[i] + "...");
	    int[] array = ArrayGen.getRand(sizes[i], dVals[i]);
	    int[] copy = copyArray(array);
	    Arrays.sort(copy);
	    LocSort.insertionSort(array, dVals[i]);
	    if(isSorted(array, copy))
		score += singleTest;
	}
	return score;
    }

    private static int testHeapSort() {
	System.out.println("\n***Testing HeapSort***");
	int score = 0;
	for(int i = 0; i < sizes.length; i++) {
	    System.out.println("\nTesting on array of size " + sizes[i] + " with d = " + dVals[i] + "...");
	    int[] array = ArrayGen.getRand(sizes[i], dVals[i]);
	    int[] copy = copyArray(array);
	    Arrays.sort(copy);
	    LocSort.heapSort(array, dVals[i]);
	    if(isSorted(array, copy))
		score += singleTest;
	}
	return score;
    }

    private static int testMergeSort() {
	System.out.println("\n***Testing MergeSort***");
	int score = 0;
	for(int i = 0; i < sizes.length; i++) {
	    System.out.println("\nTesting on array of size " + sizes[i] + " with d = " + dVals[i] + "...");
	    int[] array = ArrayGen.getRand(sizes[i], dVals[i]);
	    int[] copy = copyArray(array);
	    Arrays.sort(copy);
	    LocSort.mergeSort(array, dVals[i]);
	    if(isSorted(array, copy))
		score += singleTest;
	}
	return score;
    }

    private static int[] copyArray(int[] array) {
	int[] a = new int[array.length];
	for(int i = 0; i < array.length; i++)
	    a[i] = array[i];
	return a;
    }

    private static boolean isSorted(int[] array1, int[] array2) {
	System.out.println("Comparing the contents of the array to the expected contents...");
	if(array1.length != array2.length) {
	    System.out.println("Array lengths do not match.");
	    return false;
	}
	for(int i = 0; i < array1.length; i++) {
	    if(array1[i] != array2[i]) {
		System.out.println("Elements do not match at index " + i + ".");
		System.out.println("Expected: " + array2[i]);
		System.out.println("Actual: " + array1[i]);
		return false;
	    }
	}
	System.out.println("Arrays match!");
	return true;
    }
}
