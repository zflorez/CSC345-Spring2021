
public class Part1B {

	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);
		
		int temp = N;
		int count = 0;
		
		int sum = 0;
		while (temp > 0) {
			for(int j = 1; j <= temp; j ++) {
				for (int k = 1; k <=j; k ++) {
					sum += j + k;
					count ++;
				}
			}
			temp = temp / 3;
		}
		System.out.printf("N = %d count = %d", N, count);
	}

}
