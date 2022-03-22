import java.util.*;
import java.io.*;

public class SortGridTest {
    private static int[] N = new int[]{10, 20, 40, 80, 100};
    private static int[] c1 = new int[]{2558, 13416, 66440, 317064, 521126};
    private static int[] c2 = new int[]{10358, 161546, 2566298, 40985278, 100039962};
    private static int[] c3 = new int[]{13270, 235566, 3787132, 60586106, 147931724};
    
    public static void main(String[] args) {
	int score = 0;
	
	for(int testNum = 1; testNum <= 5; testNum++) 
	    score += test(testNum);
	System.out.println("SortGrid Total: " + score);
    }

    private static int test(int testNum) {
	int score = 0;
	System.out.println("***** BEGIN TEST " + testNum + "*****");
	Grid grid = new Grid("testGrid" + testNum + ".txt", false);
	SortGrid.sort(grid);
	score += checkAccuracy(grid);
	score += checkAccessCount(grid.getAccessCount(), c1[testNum-1], c2[testNum-1], c3[testNum-1], N[testNum-1]*N[testNum-1]);
	return score;
    }
   
    private static int checkAccessCount(int c, int c1, int c2, int c3, int Nsqrd) {
	System.out.println("Checking access count...");
	if(c <= Nsqrd) {
	    System.out.println("Access count <= N^2? Something isn't right...");
	    return 0;
	}    
	if(c <= c1) {
	    System.out.println("Access count is below the first cutoff!");
	    return 2;
	}
	else if(c <= c2) {
	    System.out.println("Access count is below the second cutoff!");
	    return 1;
	}
	else {
	    System.out.println("Access count is too high for extra credit...");
	}
	return 0;
    }

    private static double checkAccuracy(Grid grid) {
	//System.out.println(grid);
	if(grid.isSorted()) {
	    System.out.println("The grid is sorted!");
	    return 4;
	}
	System.out.println("The grid is not sorted. Use the toString method to print the grid before and after sorting.");
	return 0;
    }
}
 
