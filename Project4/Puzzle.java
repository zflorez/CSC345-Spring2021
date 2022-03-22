import java.util.ArrayList;

/**
 * @author Zachary Florez
 * 
 * This class implements a word search from a Grid. We do this 
 * by using BFS to find the source vertex and then continuously 
 * doing DFS to search for a word. 
 */

public class Puzzle {
	
		static Grid grid; 
		static Deque<Loc> DFSstack;
		static String DFSanswer;
		static ArrayList<Loc> visitedLocations;
		
	/**
	 * Creates a new Stack and Queue to use for BFS and DFS. 
	 * Also instantiates our grid to be equal to the param 
	 * grid. 
	 * 
	 * @param grid, Grid to use for searching. 
	 */
	public Puzzle(Grid inputGrid) {
		grid = inputGrid;
		DFSstack = new Deque<Loc>();
		DFSanswer = "";
		visitedLocations = new ArrayList<Loc>();
	}
	
	/**
	 * Only function that is called in order to get a path of a word. 
	 * We implement this by calling our BFS function, and then inside BFS 
	 * we call DFS to get our final solution if it exists. 
	 * 
	 * @param fullWord, String
	 * @param row, int
	 * @param col, int
	 */
	public String find(String fullWord, int row, int col) {
		
		// Reset all locations that we visited in DFS.
		for (Loc visitedLocation : visitedLocations) {
			visitedLocation.BFSvisited = false;
			visitedLocation.inQueue    = false;
		}
		
		visitedLocations = new ArrayList<Loc>();
		DFSanswer = "";
		
		// Get answer
		BFS(fullWord, grid.getLoc(row, col));
		
		return DFSanswer;
	}
	
	/**
	 * Inside this function we are given two params, first is the desired word 
	 * we will be searching for, and the second is the Location that we need 
	 * to start at inside the grid to perform BFS. 
	 * 
	 * @param word, String 
	 * @param source, Location
	 */
	private static String BFS(String word, Loc source) {
		
		String answer = ""; 
		Deque<Loc> queue = new Deque<Loc>(); 
		
		queue.addToBack(source);
		visitedLocations.add(source);
		source.inQueue = true; 
		
		String firstLetter = word.substring(0, 1);
		
		// Until Queue is not empty
		while (!queue.isEmpty()) {
			
			// v:= Q.dequeue()
			// Mark v
			Loc vertex = null;
			
			try {
				vertex = queue.getFirst();
				vertex.BFSvisited = true; 
			} catch (EmptyDequeException e) {
				e.printStackTrace();
			}
			
			// Find all adjacent values. 
			Loc[] adjacentValues = new Loc[4];
			adjacentValues[0] = grid.getLoc(vertex.row - 1, vertex.col); // up    
			adjacentValues[2] = grid.getLoc(vertex.row + 1, vertex.col); // down  
			adjacentValues[3] = grid.getLoc(vertex.row, vertex.col - 1); // left  
			adjacentValues[1] = grid.getLoc(vertex.row, vertex.col + 1); // right 
		
			// for all w adjacent to v
			for (Loc adjacentVal : adjacentValues) {
				
				// First check adjacent value is in boundaries. 
				if (adjacentVal == null)
					continue; 
				
				if (adjacentVal.getVal().equals(firstLetter)) {
					
					// Call DFS to get desired location. 
					DFS(word.substring(1), adjacentVal);
					
					if (DFSanswer.equals("")) {
						DFSstack = new Deque<Loc>();
						DFSanswer = "";
						continue;
					} else {
						return DFSanswer;
					}
				} 
				
				//if w is not in the queue and not marked
				if (!adjacentVal.BFSvisited && !adjacentVal.inQueue) {
					
					// Q.enqueue(w)
					queue.addToBack(adjacentVal);
					adjacentVal.inQueue = true;
					visitedLocations.add(adjacentVal);
				}
			} // END FOR
		} // END WHILE
		return answer; 
	}
	
	/**
	 * Recursive method for Deapth First Search. This method is called from 
	 * inside BFS function once we find a good starting location on our 2D Grid. 
	 * In this method we get the String next letter of the desired word. And then 
	 * simply follow the pseudocode from lecture slides that Melanie privided for 
	 * us. 
	 * 
	 * @param word, String desired word we are looking for 
	 * @param source that we are starting at, changed after every recursive call. 
	 * @return string
	 * @throws EmptyDequeException 
	 */
	private static void DFS(String word, Loc source) {

		// Once we get to an empty word, we know we found an answer.
		if (word.equals("")) {
			DFSstack.addToBack(source);
			while (DFSstack.size() != 0) {
	
				// Catch empty deque exception.
				try {
					DFSanswer += DFSstack.getFirst().toString();
				} catch (EmptyDequeException e) {
					e.printStackTrace();
				}
				
			} // END WHILE
			//return DFSanswer;
			return;
		}

		String nextLetter; 
		if (word.length() != 1)
			nextLetter = word.substring(0, 1);
		else 
			nextLetter = word; 
		
		// Add vertex to stack and to visited locations.
		DFSstack.addToBack(source); 
		visitedLocations.add(source);
		
		// Create adjacent values. 
		Loc[] adjacentValues = new Loc[4];
		Loc up    = grid.getLoc(source.row - 1, source.col); // up
		Loc right = grid.getLoc(source.row, source.col + 1); // right
		Loc down  = grid.getLoc(source.row + 1, source.col); // down
		Loc left  = grid.getLoc(source.row, source.col - 1); // left
		
		int size = 0;
		if (up != null    && up.getVal().equals(nextLetter)) {
			adjacentValues[size] = up;    // up
			size ++;
		}
		if (right != null && right.getVal().equals(nextLetter)) {
			adjacentValues[size] = right; // right
			size ++;
		}
		if (down != null  && down.getVal().equals(nextLetter)) { 
			adjacentValues[size] = down; // down
			size ++;
		}
		if (left != null  && left.getVal().equals(nextLetter)) {
			adjacentValues[size] = left; // left
			size ++;
		}
		
		// for each vertex w adjacent to v
		for (int i = 0; i < size; i ++) {
			
			DFS(word.substring(1), adjacentValues[i]);
			
			if (DFSstack.size() == 0)
				return;
		}
		
		// Get the last element if nothing worked out and then return back.
		if (DFSstack.size() != 0) {
			try {
				DFSstack.getLast();
			} catch (EmptyDequeException e) {
				e.printStackTrace();
			}
		}
		return;
	}
}
