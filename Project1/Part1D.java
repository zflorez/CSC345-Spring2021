
import java.util.Random;

public class Part1D {

	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);
		
		int[] A = get_best_case(N);
		int[] B = get_worst_case(N);
		int[] C = randomArray(N);
		
		
		int best = getCount(A, N);
		int worst = getCount(B, N);
		int random = getCount(C, N);
		
		System.out.printf("N = %d best = %d worst = %d random = %d", N, best, worst, random);
	}
	
	public static int getCount(int[] A, int N) {
		int count = 0;
		
		// Algorithm doSomething()
		for (int i = 1; i <= N - 1 ; i ++) {
			int j = i;
			int k = j - 1;
			count += 2;
			
			while (k >= 0 && A[j] < A[k]) {
				int temp = A[j];
				A[j] = A[k];
				A[k] = temp;
				
				count += 4;
				j--;
				k--;
			}
		}
		return count;
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Best Case Array of size N for Algorithm D. 
	  */
	public static int[] get_best_case(int N) {
		
		int[] bestArray = new int[N];
		
		for (int i = 0; i < N; i ++) {
			bestArray[i] = i;
		}
		return bestArray;
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Worst Case Array of size N for Algorithm D. 
	  */
	public static int[] get_worst_case(int N) {
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
	  * @return Random Case Array of size N for Algorithm D. 
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
