
import java.util.Random;

public class Part1E {
	
	static int COUNT;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int N = Integer.parseInt(args[0]); 
		
		int[] A = getBestArray(N);
		int[] B = getWorstArray(N);
		int[] C = randomArray(N);
		
		int best = getCount(A, N);
		int worst = getCount(B, N);
		int random = getCount(C, N);
		
		System.out.printf("N = %d best = %d worst = %d average = %d\n", N, best, worst, random);
	}
	
	public static int getCount(int[] A, int N) {
		int count = 0;
		
		// Algorithm doSomething()
		for (int i = N/2; i >= 0; i --) {
			count ++;
			foo(A, i);
		}
		
		int countArrayAccess = COUNT;
		COUNT = 0;
		return count + countArrayAccess;
	}
	
	public static void foo(int[] A, int i) {
	
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		
		if (left >= A.length && right >= A.length) {
			COUNT ++;
			return;
		}
		
		if (right >= A.length) {
			
			if (A[i] < A[left]) {
				int temp = A[i];
				A[i] = A[left];
				A[left] = temp;
				COUNT += 4;
			}
			return;
			
		} if (( A[right] > A[left]) && (A[right] > A[i]) ) {
			int temp = A[i];
			A[i] = A[right];
			A[right] = temp;
			COUNT += 4;
			
			foo(A, right);
			return;
			
		} if (A[left] > A[i]) {
			int temp = A[i];
			A[i] = A[left];
			A[left] = temp;
			COUNT += 4;
			
			foo(A, left);
		}
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Worst Case Array of size N for Algorithm E. 
	  */
	public static int[] getWorstArray(int N) {
		
		int[] bestArray = new int[N];
		
		for (int i = 0; i < N; i ++) {
			bestArray[i] = i;
		}
		return bestArray;
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Best Case Array of size N for Algorithm E. 
	  */
	public static int[] getBestArray(int N) {
		
		int[] worstArray = new int[N];
		int counter = 0;
		
		for (int i = worstArray.length -1; i >= 0; i --) {
			worstArray[counter] = i;
			counter ++;
		}
		return worstArray;
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Random Case Array of size N for Algorithm E. 
	  */
	public static int[] randomArray(int N) {
		Random rand = new Random(); // Creating random object
		int [] randomArray = new int[N];
		
		for (int i = 0; i < N; i ++) {
			randomArray[i] = rand.nextInt();
		}
		return randomArray;
	}
}
