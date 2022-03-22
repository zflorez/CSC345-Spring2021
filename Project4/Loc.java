

public class Loc {
	
	// Original set values for a location. 
    public final int row;
    public final int col;
    private String val;
    
    // Newly added values for a location. 
	public boolean inQueue;
	public boolean BFSvisited;
	public int distance;
	public Loc previous; 
	public boolean inSPQueue;

    public Loc(int x, int y, String val) {
    	
    	// Original set values for a location. 
    	this.row = x;
    	this.col = y;
    	this.val = val;
	
    	// Newly added values for a location
    	this.inQueue = false;
    	this.BFSvisited = false;
    	distance = Integer.MAX_VALUE;
    	previous = null;
    	inSPQueue = false;
    }

    //returns Loc in the form (row, col)
    public String toString() {
    	return "(" + row + ", " + col + ")";
    }

    //returns the String value at this location
    public String getVal() {
    	return val;
    }

    //returns the integer value at this location
    //NOTE: This should only be used if the location
    //contains an integer!
    public int getIntVal() {
    	return Integer.parseInt(val);
    }
}



