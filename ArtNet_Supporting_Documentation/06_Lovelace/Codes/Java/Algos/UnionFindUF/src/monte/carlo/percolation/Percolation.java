package monte.carlo.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF fullGrid;   // keeps track of full (connected) sites
    private WeightedQuickUnionUF fullGridBottom;   // keeps track of full (connected) sites
    private boolean[] openGridBoolean;       // keeps track of open sites
    private int gridHeight;                  // Height or Width of grid
    
    private int top;                        // top virtual site
    private int bottom;                     // bottom virtual site
    
 
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        // Make sure N is valid, i.e. greater then 0
        if (N > 0) {
            
            // set gridHeight to N
            gridHeight = N;      
            // 2 * Create a (virtual) 2 x 2 matrix of elements of type WeightedQuickUnionUF
            fullGrid = new WeightedQuickUnionUF(gridHeight * gridHeight + 2);
            fullGridBottom = new WeightedQuickUnionUF(gridHeight * gridHeight + 2);
            // Create a (virtual 2 x 2 matrix of type boolen
            openGridBoolean = new boolean[gridHeight * gridHeight + 2]; 
            // set the top and bottom virtual sites
            top = gridHeight * gridHeight;
            bottom = gridHeight * gridHeight + 1;
            
            
        } else // N is out of bounds
        throw new IllegalArgumentException("N is out of bounds");          
    } // end Constructor

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        
        // Set the site to be open if not already open
        int thisPoint = pointToIndex(i, j);
        if (!openGridBoolean[thisPoint]) 
            openGridBoolean[thisPoint] = true;
        
        // Add thisPoint to the set of top sites
        if (i == 1) {
           fullGrid.union(top, thisPoint);
           fullGridBottom.union(top, thisPoint);
        }
        
        // Add thisPoint to the set of bottom sites
        if (i == gridHeight) 
            fullGridBottom.union(bottom, thisPoint);    
        
        // connect site(i,j) to any open sites (left, right, above, below) of site(i,j)
        // For a connected site ABOVE site(i,j)
        if (i > 1 && isOpen(i - 1, j)) {
            fullGrid.union(thisPoint, pointToIndex(i - 1, j));
            fullGridBottom.union(thisPoint, pointToIndex(i - 1, j));
        }
                
        // For a connected site BELOW site(i,j)
        if (i < gridHeight && isOpen(i + 1, j)) {
            fullGrid.union(thisPoint, pointToIndex(i + 1, j));
            fullGridBottom.union(thisPoint, pointToIndex(i + 1, j));
        }
        // For a connected site LEFT of site(i,j)
        if (j > 1 && isOpen(i, j - 1)) {
            fullGrid.union(thisPoint, pointToIndex(i, j - 1));
            fullGridBottom.union(thisPoint, pointToIndex(i, j - 1));
        }
        
        // For a connected site RIGHT of site(i,j)
        if (j < gridHeight && isOpen(i, j + 1)) {
            fullGrid.union(thisPoint, pointToIndex(i, j + 1));  
            fullGridBottom.union(thisPoint, pointToIndex(i, j + 1));  
        }
                
    } // end open method 
    
    // Is site (i,j) open?
    public boolean isOpen(int i, int j) {
        return openGridBoolean[pointToIndex(i, j)];
    } // end isOpen method
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        // if this point is connected to the top virtual point then return true
        return fullGrid.connected(pointToIndex(i, j), top); 
        
    } // end isFull method
    
    // does the system percolate?
    public boolean percolates() {
        // if this bottom is connected to the top virtual point then return true
        return fullGridBottom.connected(bottom, top);
        
    } // end percolates method
    
    // Helper function: Converts a Point in the openGrid to an index in the fullGrid
    private int pointToIndex(int i, int j) {
        // Make sure i and j are valid inputs
        if (i < 1 || j < 1 || i > gridHeight || j > gridHeight)
            throw new IndexOutOfBoundsException("i and/or j are out of bounds");
        else 
            return (j-1) + ((i-1) * gridHeight);
    } // end pointToIndex method
    
} // end class