public class SortGrid {

    /***
     *sort the grid as described in the handout
     ***/
    public static void sort(Grid g) {
	// loop through outer array  
	for (int i = 0; i < g.size(); i ++){
		// loop inside each inner array
		for (int j = 0; j < g.size(); j ++){
			// Loop through all elements 
			for (int k = 0; k < g.size(); k ++){
				// Now we loop through to check all the elements inside the entire 
				// 2D grid with the current element we are on.
				for (int l = 0; l < g.size(); l++){
					if (g.getIntVal(i, j) < g.getIntVal(k, l)){
						g.swap(i, j, k, l);
					}
				}	
			}
	   	}	
  	}
    }
}
