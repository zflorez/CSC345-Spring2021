import java.util.ArrayList;

/**
 * 
 * @author Zachary Florez
 * 
 * This class implements finding a shortest path on a Grid. We do this 
 * by using a Priority Queue and a version of Dijkstra's Algorithm.
 *
 */

public class ShortestPath {
	
	Grid grid;
	ArrayList<Loc> changed;
	
	/**
	 * Shortest Path constructor. 
	 * @param grid
	 */
	public ShortestPath(Grid grid) {
		this.grid = grid;
		changed = new ArrayList<Loc>();
	}
	
	/**
	 * Function to get the shortest path from one point to another. 
	 * 
	 * @param x1, int first x
	 * @param y1, int first y
	 * @param x2, int second x
	 * @param y2, int second y
	 * @return String, desired shortest path from point 1 to point 2. 
	 */
	public String getShortestPath(int x1, int y1, int x2, int y2) {
		
		// Base Case
		if (baseCase(x1, y1, x2, y2)) return grid.getLoc(x1, y1).toString();
		
		String shortestPath = "";
		PQueue queue = new PQueue(this.grid.size());
		
		resetValues();
		
		// Get our starting Loc, and TARGET Loc and update distance.
		// Add starting Loc to Queue.
		Loc startingPoint = grid.getLoc(x1, y1);
		Loc endingPoint   = grid.getLoc(x2, y2);
		startingPoint.distance = startingPoint.getIntVal();
		changed.add(startingPoint);
		queue.insert(startingPoint);
		
		// Main Loop for all work 
		while (queue.length != 0) {
			
			// u <- Q.extractMin()
			Loc u = queue.deleteMin();
			u.inSPQueue = false;
			
			// Get adjacent Vertices
			Loc[] adjacentValues = getAdjacentValues(u); 
			
			// For each vertex v adjacent to G
			for (int i = 0; i < 4; i ++) {
				if (adjacentValues[i] == null) continue;
												
				// That is still in SPQueue
				if (!adjacentValues[i].inSPQueue) {
					int alt = u.distance + adjacentValues[i].getIntVal();
					
					// if alt < dist[u] + length(u, v) 
					// EDGE RELAXATION 
					if (alt < adjacentValues[i].distance) {
						edgeRelaxation(adjacentValues[i], alt, u);
						queue.insert(adjacentValues[i]);
					}
				}
				
				// Stop going through once we get to our TARGET Loc
				if (endingPoint.inSPQueue) {
					queue = new PQueue(this.grid.size());
					break;
				}
			} // END FOR
		} // END WHILE
		
		// Now add our shortest string values to the our 
		// shortestPath return string.
		shortestPath += endingPoint.toString();
		while (endingPoint.previous != null) {
			shortestPath = (endingPoint.previous.toString() + shortestPath);
			endingPoint = endingPoint.previous;
		}
		return shortestPath;
	}
	
	/**
	 * Helper function called at the start of getShortestPath 
	 * to figure out if input is at the base case. 
	 * 
	 * @param x1, int
	 * @param y1, int
	 * @param x2, int
	 * @param y2, int
	 * @return boolean, if start == end
	 */
	private boolean baseCase(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) return true;
		return false;
	}
	
	/**
	 * Helper function called to do the desired Edge Relaxation 
	 * for Dijsktra's Algoritm. 
	 * 
	 * @param loc, Loc
	 * @param alt, int
	 * @param previous, Loc
	 * 
	 * no return.
	 */
	private void edgeRelaxation(Loc loc, int alt, Loc previous) {
		loc.distance = alt; loc.previous = previous; 
		loc.inSPQueue = true; changed.add(loc);
	}
	
	/**
	 * Helper function called from shortestPath to get the adjacent 
	 * values for a specific Loc object. 
	 * 
	 * @param u, Loc
	 * @return Loc[] adjacentValues
	 */
	private Loc[] getAdjacentValues(Loc u) {
		
		Loc[] adjacentValues = new Loc[4];
		
		adjacentValues[0] = grid.getLoc(u.row - 1, u.col); // up
		adjacentValues[1] = grid.getLoc(u.row, u.col + 1); // right
		adjacentValues[2] = grid.getLoc(u.row + 1, u.col); // down
		adjacentValues[3] = grid.getLoc(u.row, u.col - 1); // left
		
		return adjacentValues; 
		
	}
	
	/**
	 * Helper function called at the start of the function
	 * shortest path to reset all the values we changed in 
	 * the last iteration.
	 * 
	 * no return. 
	 */
	private void resetValues() {
		
		// Reset changed Loc objects from previous iteration and 
		// reset our changed ArrayList. 
		for (Loc changedLocation : changed) {
			changedLocation.distance = Integer.MAX_VALUE;
			changedLocation.inSPQueue = false;
			changedLocation.previous = null;
		}
		changed = new ArrayList<Loc>();
	}
}
