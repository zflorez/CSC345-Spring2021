
import java.util.Random;

public class Part1C {

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		
		int[] A = get_best_case(N);
		int[] B = get_worst_case(N);
		int[] C = randomArrayGenerator(N);
		
		int best = getCount(A, N);
		int worst = getCount(B, N);
		int avg = getCount(C, N);
		
		System.out.printf("N = %d best = %d worst = %d avg = %d", N, best, worst, avg);
	}
	
	/**
	  * 
	  * @param N integer size of array. 
	  * @return Best Case Array of size N for Algorithm C. 
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
	  * @return Worst Case Array of size N for Algorithm C. 
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
	  * @return Random Case Array of size N for Algorithm C. 
	  */
	public static int[] randomArrayGenerator(int N) {
		Random rand = new Random(); // Creating random object
		int [] randomArray = new int[N];
		
		for (int i = 0; i < N; i ++) {
			randomArray[i] = rand.nextInt();
		}
		return randomArray;
	}
	
	
	public static int getCount(int[] A, int N) {
		
		int count = 0;
		int temp = 0;
		int x = A[0];
		int p = 0;
		int f = 1;
		
		for (int i = 1; i < N; i ++) {
			
			if (A[i] < x) {
				
				temp = A[i];
				A[i] = A[f];
				A[f] = temp;
				
				temp = A[p];
				A[p] = A[f];
				A[f] = temp;
				count += 4;
				
				p++; 
				f++;
				
			} else if (A[i] == x) {
				
				temp = A[f];
				A[f] = A[i];
				A[i] = temp;
				count+= 2;
				f++;
			}
			count ++;
		}
		return count;
	}
}
