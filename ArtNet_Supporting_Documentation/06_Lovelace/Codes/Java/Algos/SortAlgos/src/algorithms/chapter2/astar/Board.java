package algorithms.chapter2.astar;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Board {
    
    private int[][] board;
     
    private int N;
    
    // Constructor
    public Board(int[][] blocks) {
        
        if (blocks == null) {
            throw new NullPointerException("array blocks is null");
        
        } else {
            
            N = blocks.length;
            //  Create a copy of blocks for immutability reasons
            board = new int[N][N];  
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) 
                    board[i][j] = blocks[i][j]; 
        }
    }
    
    // returns the board dimension
    public int dimension() {
        
        return N;
    }
    
    // returns the value of the hamming function
    public int hamming() {
        
        int[][] goalBoard = new int[N][N];  // create the homeBoard for calculating hammering and manhattan functions
        int goalCount = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                goalBoard[i][j] = board[i][j];
                if (goalCount == N * N)
                    goalBoard[i][j] = 0;
                else
                    goalBoard[i][j] = goalCount++;
            }    
        
        int hammingCount = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] != goalBoard[i][j] 
                        && goalBoard[i][j] != 0)
                    hammingCount++;
        
        return hammingCount;        
    }
    
    // Got this from http://stackoverflow.com/questions/12526792/manhattan-distance-in-a. 
    // I was spending too long on this method
    public int manhattan() {
        int manhattanCount = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                int position = board[i][j];
                if (position != 0) { 
                    int goalI = (position - 1) / N;
                    int goalJ = (position - 1) % N; 
                    int diffI = i - goalI; 
                    int diffJ = j - goalJ; 
                    manhattanCount += Math.abs(diffI) + Math.abs(diffJ); 
                } 
            }
        return manhattanCount;
    }
    
    // equals
    public boolean equals(Object y) {
        // optimize for true object equality
        if (y == this) return true;
        
        // check for null
        if (y == null) return false;

        // objects must be in the same class. religion: getClass() vs. instanceof)
        if (y.getClass() != this.getClass()) return false;

        // cast is guaranteed to succeed
        Board that = (Board) y;
        
        // check this and that have the same dimensions
        if (this.N != that.N) return false;
        
        // check that all significant fields are the same
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
//                StdOut.println("i: " + i + " j: " + j +  " board: " + board[i][j] + " that.board: " + that.board[i][j]);
                if (board[i][j] != that.board[i][j])
                    
                    return false;
                }
        
        return true;
        
    }
    
    // is this the Goal?
    public boolean isGoal() {
        
        int[][] goalBoard = new int[N][N];  // create the homeBoard for calculating hammering and manhattan functions
        int goalCount = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                goalBoard[i][j] = board[i][j];
                if (goalCount == N * N)
                    goalBoard[i][j] = 0;
                else
                    goalBoard[i][j] = goalCount++;
            }    
        
     // check that all significant fields are the same
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
//                StdOut.println("i: " + i + " j: " + j +  " board: " + board[i][j] + " goalBoard: " + goalBoard[i][j]);
                if (board[i][j] != goalBoard[i][j])
                    
                    return false;
                }
        
        return true;
    }
    
 // board obtained by exchanging any pair of blocks
  public Board twin() {
      
      Random rn = new Random();
      
      // generate random indices (i,j) where broad[i,j] != null
      int p1rI = rn.nextInt(N); //StdRandom.uniform(N);
      int p1rJ = rn.nextInt(N); //StdRandom.uniform(N);
      int p2rI = rn.nextInt(N); //StdRandom.uniform(N);
      int p2rJ = rn.nextInt(N); //StdRandom.uniform(N);
      
      while (this.board[p1rI][p1rJ] == 0 || this.board[p2rI][p2rJ] == 0
              || (this.board[p1rI][p1rJ]) == this.board[p2rI][p2rJ]) {
          p1rI = rn.nextInt(N);
          p1rJ = rn.nextInt(N);
          p2rI = rn.nextInt(N);
          p2rJ = rn.nextInt(N);
      }  
      
      int[][] copy = new int[board.length][board.length];   
      for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
              copy[i][j] = board[i][j];
      
      int tmp = copy[p1rI][p1rJ];
      copy[p1rI][p1rJ] = copy[p2rI][p2rJ];
      copy[p2rI][p2rJ] = tmp;
      
      
      return new Board(copy);
  }
    
    // Convert board to a string
    public String toString() {
        
        StringBuilder sb = new StringBuilder(N * N);
        // add N to sb
        sb.append(N);
        sb.append("\n");
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(" ");
                sb.append(board[i][j]);
                
                if (j == (N-1)) 
                    sb.append("\n");     
            }
        }    
        return sb.toString();
    }

    // returns all neighbouring boards
    public Iterable<Board> neighbors() {
        
        return new BoardIterable();
    }
    
    // helper function for neighbours:  Hides the core functionality from clients
    private class BoardIterable implements Iterable<Board> {
        
        // List (Collection) of neighbouring boards 
        private List<Board> neighboursList = new ArrayList<Board>();
        
        private BoardIterable() {
            // Generate neighbouring boards here
            // Find point with 0 value
            int zeroI = 0;
            int zeroJ = 0;
            for (int i = 0; i < N; i++)
               for (int j = 0; j < N; j++)
                   if (board[i][j] == 0) {
                       zeroI = i;
                       zeroJ = j;   
                   }

            // check for potential moves
            // left
            if ((zeroJ-1) >= 0) 
                createNeighbour(zeroI, zeroJ, 0, -1);
            
            // right
            if ((zeroJ+1) < N)
                createNeighbour(zeroI, zeroJ, 0, 1);
            
            // up
            if ((zeroI-1) >= 0) 
                createNeighbour(zeroI, zeroJ, -1, 0);
            
            // down
            if ((zeroI+1) < N)
                createNeighbour(zeroI, zeroJ, 1, 0);
            
           }
                              
           // Helper function
           private void createNeighbour(int zeroI, int zeroJ, int vertical, int horizontal) {
               
               int[][] copy = new int[board.length][board.length];   

               for (int i = 0; i < N; i++)
                   for (int j = 0; j < N; j++)
                       copy[i][j] = board[i][j];
               
               int tmp = copy[zeroI][zeroJ];
               copy[zeroI][zeroJ] = copy[zeroI + vertical][zeroJ + horizontal];
               copy[zeroI + vertical][zeroJ + horizontal] = tmp;
               
               Board neighbour = new Board(copy);
               neighboursList.add(neighbour);
               
           }
        
        @Override
        public Iterator<Board> iterator() {
            
            Iterator<Board> iter = neighboursList.iterator();
            return iter; 
                   
        }   
    }
}


