package server;

import java.util.List;

public class SquareBoard implements Board {
    
    // Rep
    private Square[] board; // MineSweeper board
    private final int xDim;
    private final int yDim;
    private final int squares;
    private int numPlayers;
    
    // Constructor (SIZE)
    public SquareBoard( List<Integer> params, boolean debug ) {
        
        this.xDim = params.get(0);
        this.yDim = params.get(1);
        this.squares = xDim * yDim;
        board = new Square[ squares ];
        
        // Remove xDim and yDim from params
        params.remove(0);
        params.remove(0);
        
        // set bomb positions
        boolean bombState;
        for ( int i = 0; i < squares; i++ ) {
            // if the position i is in params
            if (params.contains(i))
                bombState = true;
            else
                bombState = false;
            board[ i ] = new Square("-", bombState); // create MineSweeper board
        }    
        
        numPlayers = 0; // set number of Players
        
    }

    // RepCheck Function
    private void RepCheck() {        
        assert numPlayers >= 0; 
        assert board.length == squares;
    }

    @Override
    public synchronized String[] look() {
        return makePrettyBoard();
    }

    @Override
    public synchronized String[] dig(int x, int y) {
        
        // Error Check
        if (x < 0 | y < 0)
                return makePrettyBoard();
        if ( x >= xDim | y >= yDim)
            return makePrettyBoard();
        
        // check if TOUCHED
        if (!board[convert(x, y)].getState().equals("-"))
                return makePrettyBoard();   
                
        if (board[convert(x,y)].hasBomb()) {
            String [] boom = new String[1];
            boom[0]="BOOM!";
            board[convert(x,y)].setBomb(false);
            
            // Update bombCount for this square
            updateBombCountSquare(x, y);
            
            // update bombCount for neighbours
            updateBoomNeighbours(x, y);
            
            // Step 4: need to recursively visit neighbouring squares and do something - not sure what
            
            return boom;
        } else { // update bomb count for this square
         // Update bombCount for this square
            updateBombCountSquare(x, y);
        }
            
        RepCheck();
        return makePrettyBoard();
    }

    @Override
    public synchronized String[] flag(int x, int y) {
        // Error Check
        if (x < 0 | y < 0)
                return makePrettyBoard();
        if ( x >= xDim | y >= yDim)
            return makePrettyBoard();
        
        // check if UNTOUCHED
        if (board[convert(x, y)].getState().equals("-")) {
            board[convert(x,y)].changeState("F");
        } 
        
        RepCheck();
        return makePrettyBoard();
    }

    @Override
    public synchronized String[] deflag(int x, int y) {
        // Error Check
        if (x < 0 | y < 0)
                return makePrettyBoard();
        if ( x >= xDim | y >= yDim)
            return makePrettyBoard();
        
        if (board[convert(x, y)].getState().equals("F"))
            board[convert(x,y)].changeState("-");
        RepCheck();
        return makePrettyBoard();
    }

    @Override
    public int getRows() {
        return Integer.valueOf(xDim);
    }

    @Override
    public int getCols() {
        return Integer.valueOf(yDim);
    }

    @Override
    public synchronized String addPlayer() {
        this.numPlayers++;
        RepCheck();
        return String.valueOf(numPlayers);
    }

    @Override
    public synchronized void removePlayer() {
        this.numPlayers--;
        RepCheck();
    }
    
    @Override
    public synchronized int getNumPlayers() {
        return Integer.valueOf(numPlayers);
    }
    
    /** Helper Functions 
     * 
     */
    
    //Helper Function: convert from 1 D to 2 D coordinates
    private int convert(int x, int y) {
        assert x < xDim;
        assert y < yDim;
        
        if (y == 0)
            return x;
        else if (x== 0)
            return y * xDim;
        else
            return x + xDim*y;
    }
    
    
    
    // Helper Function: return a pretty board. Also acts as a defensive copy
    private String[] makePrettyBoard() {
        
        String[] prettyBoard = new String[yDim];
        
        for (int i = 0; i < yDim; i++ ) {
            StringBuilder sb = new StringBuilder(xDim);
            for (int j = 0; j < xDim; j++) {
                sb.append(board[convert(j, i)].getState());
            }
            prettyBoard[i] = sb.toString();
        }
        return prettyBoard;    
        
    }
    
    
    
    // count neighbouring bombs
    private int bombNeighbours(int x, int y) {
        int bombCount = 0;
        
        // ABOVE
        if (y != 0) {
            // Directly ABOVE
            if (board[convert(x, y-1)].hasBomb())
                bombCount++;
            // ABOVE LEFT
            if (x != 0) {
                if (board[convert(x-1, y-1)].hasBomb())
                    bombCount++;
            }
            // ABOVE RIGHT
            if (x != xDim - 1) {
                if (board[convert(x+1, y-1)].hasBomb())
                    bombCount++;
            }    
        } // end ABOVE
        
        // ON SAME X PLANE
        // LEFT
        if (x != 0) {
            if (board[convert(x-1, y)].hasBomb()) 
                bombCount++;
        }  
        // RIGHT
        if (x != xDim - 1) {
            if (board[convert(x+1, y)].hasBomb())
                bombCount++; 
        }
            
            
        // BELOW
        if ( y != yDim - 1) {
            // Directly Below
            if (board[convert(x, y+1)].hasBomb()) 
                bombCount++;
            // BELOW LEFT
            if (x != 0) {    
                if (board[convert(x-1, y+1)].hasBomb()) 
                    bombCount++; 
            }
            // BELOW RIGHT
            if (x != xDim - 1) {
                if (board[convert(x+1, y+1)].hasBomb())
                    bombCount++; 
            }    
        } // end ABOVE
        return bombCount;
    } // end method
    
    // update neighbours bomb count after a BOOM!
    private void updateBoomNeighbours(int x, int y) {
        
        // ABOVE
        if (y != 0) {
            // Directly ABOVE
            updateBombCountNeighbour(x, y-1);
            // ABOVE LEFT
            if (x != 0) {
                updateBombCountNeighbour(x-1, y-1);
            }
            // ABOVE RIGHT
            if (x != xDim - 1) {
                updateBombCountNeighbour(x+1, y-1);
            }    
        } // end ABOVE
        
        // ON SAME X PLANE
        // LEFT
        if (x != 0) {
            updateBombCountNeighbour(x-1, y);
        }  
        // RIGHT
        if (x != xDim - 1) {
            updateBombCountNeighbour(x+1, y);
        }
                
        // BELOW
        if ( y != yDim - 1) {
            // Directly Below
            updateBombCountNeighbour(x, y+1);
            // BELOW LEFT
            if (x != 0) {    
                updateBombCountNeighbour(x-1, y+1);
            }
            // BELOW RIGHT
            if (x != xDim - 1) {
                updateBombCountNeighbour(x+1, y+1); 
            }    
        } // end ABOVE
        
    } // end method
    
    // update Boom square
    private void updateBombCountSquare(int x, int y) {
        int bombCount = bombNeighbours(x, y);
        if (bombCount == 0) {
            board[convert(x,y)].changeState(" ");
        } else {
            board[convert(x,y)].changeState(String.valueOf(bombCount));
        }
    }
    
    // update the neighbours of a BOOM square
    private void updateBombCountNeighbour(int x, int y) {
        int bombCount = bombNeighbours(x, y);
        if (bombCount == 0) {
            if (!board[convert(x,y)].getState().equals("-"))
                board[convert(x,y)].changeState(" ");
        } else {
            board[convert(x,y)].changeState(String.valueOf(bombCount));
        }
    }

}
