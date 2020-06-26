package algorithms.strings.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithms.strings.assignment.BoggleBoard;
import algorithms.strings.assignment.BoggleSolver;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolveTest {

    @Before
    public void setUp() throws Exception {
        
        
        // initialize a 4-by-4 board from a file
        String filename = "/home/gavin/Java/Algos/week10/boggle/dictionary-algs4.txt";
        StdOut.println("Dictionary from file " + filename + ":");
        // Create temp set of dictionary words
        SET<String> tempSetDict = new SET<String>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            tempSetDict.add(in.readLine());
        }
        // Create a String array of words
        String[] dict = new String[tempSetDict.size()];
        int count = 0;
        for (String word: tempSetDict) {
            dict[count++] = word;
        }
        // Create a Bogglesolver
        BoggleSolver solver1 = new BoggleSolver(dict);
        
        // initialize a 4-by-4 board using Hasbro dice
        StdOut.println("Hasbro board:");
        BoggleBoard board1 = new BoggleBoard();
      
        // initialize a 4-by-4 board from a 2d char array
        StdOut.println("4-by-4 board from 2D character array:");
        char[][] a =  {
          { 'D', 'O', 'T', 'Y' },
          { 'T', 'R', 'S', 'F' },
          { 'M', 'X', 'M', 'O' },
          { 'Z', 'A', 'B', 'W' }
        };
        BoggleBoard board2 = new BoggleBoard(a);
      
        // initialize a 4-by-4 board from a file
        String filenameBoard = "/home/gavin/Java/Algos/week10/boggle/board-q.txt";
      
        StdOut.println("4-by-4 board from file " + filenameBoard + ":");
        BoggleBoard board3 = new BoggleBoard(filenameBoard);
        StdOut.println(board3);
        StdOut.println();
      
      
      // Print out all valid words on the board
        for (String word1: solver1.getAllValidWords(board3))
            StdOut.println("word: " + word1 + " score: " + solver1.scoreOf(word1));
        }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
