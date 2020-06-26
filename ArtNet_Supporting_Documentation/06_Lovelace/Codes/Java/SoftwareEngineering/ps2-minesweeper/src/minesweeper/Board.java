package minesweeper;

import java.util.List;

public interface Board {
    
 // Spec:
    // Abstract Function
        // Represents all the valid moves made by players
    // Rep Invariant
        // Length of board must always be equal to the variable squares
    
    // Factory method
    public static  Board makeBoard( List<Integer> params, boolean debug ) {
        return new SquareBoard( params, debug );
    }
    
    // state of the playing board
    public String[] look();
    
    // dig at a position (x, y)
    public String[] dig( int x, int y);
    
    // set a flag at a position (x, y)
    public String[] flag( int x, int y);
    
    // de-flag at a position (x,y)
    public String[] deflag( int x, int y);
    
    // get number of rows
    public int getRows();
    
    // get number of columns
    public int getCols();
    
    // add player
    public String addPlayer();
    
    // remove player
    public void removePlayer();
    
    // get number of existing players
    public int getNumPlayers();

}
