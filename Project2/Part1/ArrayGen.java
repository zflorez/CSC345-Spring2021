import java.util.Random;

public class ArrayGen {
    public static int[] genSortAsc(int n) {
	Random gen = new Random(System.currentTimeMillis());
	int[] array = new int[n];
	int val = gen.nextInt(10);
	array[0] = val;
	for(int i = 1; i < n; i++) 
	    array[i] = array[i-1] + gen.nextInt(10);
	return array;
    }

    public static int[] genSortDesc(int n) {
	Random gen = new Random(System.currentTimeMillis());
	int[] array = new int[n];
	int val = gen.nextInt(1000) - 500;
	array[0] = val;
	for(int i = 1; i < n; i++)
	    array[i] = array[i-1] - gen.nextInt(10);
	return array;
    }

    public static int[] getRand(int n, int d) {
	int[] array = genSortAsc(n);
	shuffle(array, d);
	return array;
    }

    private static void shuffle(int[] array, int d) {
	boolean[] moved = new boolean[array.length];
	Random gen = new Random(System.currentTimeMillis());
	for(int i = 0; i < array.length; i++) {
	    int shift = gen.nextInt(2*d+1) - d;
	    if(shift != 0 && i + shift >= 0 && i + shift < array.length && !moved[i] && !moved[i+shift]) {
		swap(array, i, i + shift);
		moved[i] = true;
		moved[i+shift] = true;
	    }
	}
    }

    private static void swap(int[] array, int i, int j) {
	if (i != j) {
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	}
    }
}
	

    
