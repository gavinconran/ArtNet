package algorithms.chapter2.test;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithms.chapter2.astar.Board;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class aStarTest {
    
    int[][] nullBlocks;
    
    int[][] blocks1;
    int[][] blocks2;
    int[][] blocks3;
    int[][] blocks4;
    int[][] blocks5;
    int[][] blocks2by2;
    int[][] goal1;
    int[][] goal2;
    
    int[][] blocks17;
    int[][] blocks27;


    @Before
    public void setUp() throws Exception {
        
        nullBlocks = null;
        
        In inTest1 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test1.txt");
        int N = inTest1.readInt();
        blocks1 = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks1[i][j] = inTest1.readInt();
        
        In inTest2 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test2.txt");
        int M = inTest2.readInt();
        blocks2 = new int[M][M];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < M; j++)
                blocks2[i][j] = inTest2.readInt();
        
        In inTest3 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test3.txt");
        int O = inTest3.readInt();
        blocks3 = new int[O][O];
        for (int i = 0; i < O; i++)
            for (int j = 0; j < O; j++)
                blocks3[i][j] = inTest3.readInt();
        
        In inTest4 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test4.txt");
        int P = inTest4.readInt();
        blocks4 = new int[P][P];
        for (int i = 0; i < P; i++)
            for (int j = 0; j < P; j++)
                blocks4[i][j] = inTest4.readInt();
        
        In inTest5 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test5.txt");
        int test5 = inTest5.readInt();
        blocks5 = new int[test5][test5];
        for (int i = 0; i < test5; i++)
            for (int j = 0; j < test5; j++)
                blocks5[i][j] = inTest5.readInt();
        
        
        In inGoal3 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/goal3.txt");
        int G = inGoal3.readInt();
        goal1 = new int[G][G];
        goal2 = new int[G][G];
        for (int i = 0; i < G; i++)
            for (int j = 0; j < G; j++) {
                goal1[i][j] = inGoal3.readInt();nullBlocks = null;
                goal2[i][j] = goal1[i][j];
            }    
        
        In inTest2by2 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/test2by2.txt");
        int T2by2 = inTest2by2.readInt();
        blocks2by2 = new int[T2by2][T2by2];
        for (int i = 0; i < T2by2; i++)
            for (int j = 0; j < T2by2; j++)
                blocks2by2[i][j] = inTest2by2.readInt();
        
        In inPuzzle17 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/puzzle17.txt");
        int Puzz17 = inPuzzle17.readInt();
        blocks17 = new int[Puzz17][Puzz17];
        for (int i = 0; i < Puzz17; i++)
            for (int j = 0; j < Puzz17; j++)
                blocks17[i][j] = inPuzzle17.readInt();
        
        In inPuzzle27 = new In("/home/gavin/Java/Algos/week4/assignment/testFiles/puzzle27.txt");
        int Puzz27 = inPuzzle27.readInt();
        blocks27 = new int[Puzz27][Puzz27];
        for (int i = 0; i < Puzz17; i++)
            for (int j = 0; j < Puzz27; j++)
                blocks27[i][j] = inPuzzle27.readInt();
    }

    @After
    public void tearDown() throws Exception {
        
        blocks1 = null;
        blocks2 = null;
        blocks3 = null;
        blocks4 = null;
        blocks5 = null;
        blocks2by2 = null;
        goal1 = null;
        goal2 = null;
        
        blocks17 = null;
        blocks27 = null;

        
    }

    @Test(expected=NullPointerException.class)
    public void testBoardConstructorNulPointerException() {
        
        new Board(nullBlocks);
    }
    
    @Test
    public void testBoardConstructor() {
        
        new Board(blocks1);
    }
    
    @Test
    public void testDimension() {
        
        Board board = new Board(blocks1);
        assertTrue(board.dimension() == 3); 
    }
    
    @Test
    public void testHamming() {
        
        Board board = new Board(blocks1);
        Board board17 = new Board(blocks17);
        Board board27 = new Board(blocks27);
        StdOut.println(board27.toString());
        assertTrue(board.hamming() == 4); 
        assertTrue(board17.hamming() == 8); 
        assertTrue(board27.hamming() == 7); 
    }
    
    @Test
    public void testManhattan() {
        
        Board board = new Board(blocks1);
        assertTrue(board.manhattan() == 4); 
        Board board5 = new Board(blocks5);
        Board board27 = new Board(blocks27);
        assertTrue(board5.manhattan() == 10); 
        assertTrue(board27.manhattan() == 17); 
    }
    
    @Test
    public void testEquals() {
        
        Board board1 = new Board(blocks1);
        Board board2 = new Board(blocks2);
        Board board3 = new Board(blocks3);
        Board boardGoal = new Board(goal1);
        Board boardGoal2 = new Board(goal2);
        Board board2by2 = new Board(blocks2by2);

        assertTrue(board1.equals(board2)); 
        assertFalse(board1.equals(board3)); 
        assertTrue(boardGoal.equals(boardGoal)); 
        assertTrue(boardGoal.equals(boardGoal2)); 
        assertFalse(board1.equals(board2by2)); 
    }
    
    @Test
    public void testIsGoal() {
        
        Board board = new Board(blocks1);
        Board boardGoal = new Board(goal1);
        assertFalse(board.isGoal()); 
        assertTrue(boardGoal.isGoal()); 
    }
    
    @Test
    public void testTwin() {
        
        Board board = new Board(blocks1);
        assertFalse(board.equals(board.twin())); 
    }
    
    @Test
    public void testToString() {
        
        Board board = new Board(blocks1);
        StdOut.println(board.toString());
        
    }
    
    @Test
    public void testNeighbors() {   
        
      Board board = new Board(blocks1);      
      Iterable<Board> boardIter = board.neighbors();
      for (Board neighbour: boardIter)
          StdOut.println(neighbour.toString());      

      Board board4 = new Board(blocks4);     
      Iterable<Board> boardIter4 = board4.neighbors();
      for (Board neighbour: boardIter4)
          StdOut.println(neighbour.toString());   
         
      Board board3 = new Board(blocks3);          
      Iterable<Board> boardIter3 = board3.neighbors();
      for (Board neighbour: boardIter3)
          StdOut.println(neighbour.toString());   
        
    }

}
