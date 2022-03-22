import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SPTest {
    private static int[][] test1Params = new int[][] {{0, 0, 6, 6}, {3, 1, 4, 5}, {1, 5, 1, 5}, {0, 6, 6, 0}};
    private static int[][] test2Params = new int[][] {{0, 0, 13, 13}, {3, 1, 4, 1}, {5, 9, 2, 13}, {6, 2, 8, 1}};
    private static int[][] test3Params = new int[][] {{0, 27, 27, 0}, {3, 14, 15, 9}, {1, 6, 2, 8}, {19, 8, 7, 0}};
    private static int[][] test4Params = new int[][] {{55, 55, 0, 0}, {49, 6, 6, 28}, {19, 8, 7, 33}, {5, 5, 6, 6}};
    private static int[][] test5Params = new int[][] {{100, 100, 33, 33}, {0, 111, 111, 0}, {62, 81, 98, 7}, {33, 49, 66, 94}};
    private static String[] paths;
    private static int[] accessCounts;
    
    public static void main(String[] args) {
	int score = 0;
	for(int i = 1; i <= 5; i++) 
	    score += runTest(i);
    }

    private static int runTest(int num) {
	System.out.println("***TEST " + num + "***\n");
	int score = 0;
	int[][] params = null;
	if(num == 1) {
	    params = test1Params;
	} else if(num == 2) {
	    params = test2Params;
	} else if(num == 3) {
	    params = test3Params;
	} else if(num == 4) {
	    params = test4Params;
	} else {
	    params = test5Params;
	}
	String fn = "grid" + num + ".txt";
	Grid grid = new Grid(fn, false);
	ShortestPath sp = new ShortestPath(grid);
	setExpected(num);
	for(int i = 0; i < 4; i++) {
	    int x1 = params[i][0];
	    int y1 = params[i][1];
	    int x2 = params[i][2];
	    int y2 = params[i][3];
	    System.out.println("Searching for the shortest path from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")...");
	    String path = sp.getShortestPath(x1, y1, x2, y2);
	    int count = grid.getAccessCount();
	    score += checkPath(path, paths[i]);
	    score += checkCounts(count, accessCounts[i]);
	    grid.resetAccessCount();
	}
	System.out.println("\nTest " + num + " expected score: " + score + "\n");
	return score;
    }

    private static int checkPath(String act, String exp) {
	if(act != null && act.equals(exp)) {
	    System.out.println("Found correct path!\n");
	    return 1;
	}
	System.out.println("Failed to find correct path...");
	System.out.println("Expected: " + exp);
	System.out.println("Actual:   " + act + "\n");
	return 0;
    }

    private static int checkCounts(int act, int exp) {
	if(act < 1) {
	    System.out.println("Access count is way to low! Something isn't right...");
	    return 0;
	}
	if(act <= exp)
	    return 1;
	System.out.println("Access count is higher than expected!");
	System.out.println("Expected: " + exp);
	System.out.println("Actual: " + act + "\n"); 
	return 0;
    }

    private static void setExpected(int testNum) {
	String fn = "sp_output" + testNum + ".txt";
	paths = new String[4];
	accessCounts = new int[4];
	BufferedReader reader;
	try {
	    reader = new BufferedReader(new FileReader(fn));
	    int i = 0;
	    while(i < 4) {
		String line = reader.readLine();
		if(line != null)
		    paths[i] = line;
		line = reader.readLine();
		if(line != null)
		    accessCounts[i] = Integer.parseInt(line);
		i++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
