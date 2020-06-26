package algorithms.chapter2.astar;

import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver { 

    // Class search node is the "Key" placed on the MinPQ
    private static class SearchNode {
        
        public static final Comparator<SearchNode> BY_PRIORITY = new ByPriority();
        
        private Board board;        // this.board
        private int moves;          // number of moves to get to this board
        private int manhattan;      // manhattan distance between this.board and the goal board
        private int priority;       // priority = moves + manhattan
        private Board previous;     // previous board
        
        private SearchNode previousSearchNode;
        
        // Constructor
        public SearchNode(Board board, int moves, Board previous, SearchNode previousSearchNode) {
            
            this.board = board;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.priority = this.moves + this.manhattan;
            this.previous = previous;
            this.previousSearchNode = previousSearchNode;
        }
        
        public Board getBoard() {
            return this.board;
        }
        
        public int getMoves() {
            return this.moves;
        }
        
        public int getPriority() {
            return this.priority;
        }
        
        public Board getPrevious() {
            return this.previous;
        }
        
        public SearchNode getPreviousSearchNode() {
            return this.previousSearchNode;
        }
        
        // needed by MinPQ to decide on the lowest priority based on a searchnode's priority
        private static class ByPriority implements Comparator<SearchNode> {

            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return Integer.compare(o1.getPriority(), o2.getPriority());

            }    
        }
    }
    
    private MinPQ<SearchNode> minPQ;        // Minimum Priority Queue
    private MinPQ<SearchNode> twinMinPQ;    // twin Minimum Priority Queue
    private int moves;                      // number of moves to goal board; -1 if unsolvable
    private boolean solvable;               // is it solvable?
    private SearchNode solution;            // A pointer to the searchNode with the goal as it's board
        
    
    public Solver(Board initial) {
        
        if (initial == null) {
            
            throw new NullPointerException("array blocks is null");
        } else {
            
            // Create an initial SearchNode and twinSearchNode 
            SearchNode searchNode = new SearchNode(initial, 0, initial, new SearchNode(initial, 0, initial, null));
            Board twinInitial = initial.twin();
            SearchNode twinSearchNode = new SearchNode(twinInitial, 0, null, null);
            
            // Create a minimum Priority Queue and a twin Min PQ: Min. Priority to be decided by the searchNode's priority
            minPQ = new MinPQ<SearchNode>(SearchNode.BY_PRIORITY);
            twinMinPQ = new MinPQ<SearchNode>(SearchNode.BY_PRIORITY);

            // insert searchNode and twinSearchNode into minPQ and twinMinPQ respectively
            minPQ.insert(searchNode);
            twinMinPQ.insert(twinSearchNode);
            
            // set moves to zero
            moves = 0;
            // set solvable to true - will be set to false if unsolvable
            solvable = true;
            // initialise solution which points to the initial searchNode
            solution = searchNode;
            
            // while the current search node board is not the goal
            while (!searchNode.getBoard().isGoal()) {
                // delete the min priority search node from both the minPQ and twinMinPQ
                searchNode = minPQ.delMin();
                twinSearchNode = twinMinPQ.delMin();

                // point the solution to the present searchNode
                solution = searchNode;
                       
                // add all viable neighbours of the current search node board to the minPQ
                for (Board neighbor: searchNode.getBoard().neighbors()) {
                    if (!neighbor.equals(searchNode.getPrevious()))
                        minPQ.insert(new SearchNode(neighbor, 
                                searchNode.getMoves() + 1, 
                                searchNode.getBoard(),
                                searchNode)); // present searchNode is neighbour's previous searchNode    
                }
                
                // add all viable neighbours of the current twin search node board to the twinMinPQ
                for (Board twinNeighbor: twinSearchNode.getBoard().neighbors()) {
                    if (!twinNeighbor.equals(twinSearchNode.getPrevious()))
                        twinMinPQ.insert(new SearchNode(twinNeighbor, 
                                twinSearchNode.getMoves() + 1, 
                                twinSearchNode.getBoard(),
                                null));    
                }
                
                this.moves = solution.getMoves();
                
                // if the twin solution is solvable then the solution is unsolvable
                if (twinSearchNode.getBoard().isGoal()) {
                    solvable = false;
                    this.moves = -1;     // unsolvable so set moves to -1
                    break; // solution is unsolvable so break from the while loop
                }
            }
        }
    }
    
    // returns true if the solution is solvable (or false if unsolvable)
    public boolean isSolvable() {
        
        return solvable;
    }
    
    // returns the number of moves to reach the goal or -1 if the solution is unsolvable
    public int moves() {

            return this.moves;
    }

//    @Override
    public Iterable<Board> solution() {
        
        if (isSolvable())
            return new BoardIterable();
        else
            return null;
    
        
    }

    // helper function for neighbours:  Hides the core functionality from clients
    private class BoardIterable implements Iterable<Board> {
        
        @Override
        public Iterator<Board> iterator() {
            
            // Stack: Last In First out for proper ordering of solution
            // Initial --> Goal
            Stack<Board> iterBoards = new Stack<Board>();
            // Set thisSolution to point to solution searchNode
            SearchNode thisSolution = solution;
            for (int i = 0; i <= moves; i++) {                
                iterBoards.push(thisSolution.getBoard());
                thisSolution = thisSolution.getPreviousSearchNode();
            }
            
            Iterator<Board> iter = iterBoards.iterator(); 
            return iter;
        }   
    }
    
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
