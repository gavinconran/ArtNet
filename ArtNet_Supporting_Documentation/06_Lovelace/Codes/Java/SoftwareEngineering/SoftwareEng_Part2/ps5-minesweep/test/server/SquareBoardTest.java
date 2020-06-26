package server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SquareBoardTest {

    @Test
    public void testGetDimensions() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(-1);
        
        Board board = new SquareBoard(params, true);
        assertEquals(3, board.look().length);
        
        assertEquals(3, board.getRows());
        assertEquals(3, board.getCols());
    }
    
    @Test
    public void testCreateMineSweeperBoard() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        assertEquals(3, board.look().length);
        
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]);   
    }
    
    @Test
    public void testFlag() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.flag(0, 0);
        assertEquals("F--", board.look()[0]);
        
        board.flag(1, 0);
        assertEquals("FF-", board.look()[0]);
        
        board.flag(2, 0);
        assertEquals("FFF", board.look()[0]);   
    }
    
    @Test
    public void testFlag2() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.dig(0, 0);
        assertEquals("1--", board.look()[0]);
        
        board.flag(0, 0);
        assertEquals("1--", board.look()[0]);
         
    }
    
    @Test
    public void testInvalidFlag() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.flag(0, 3);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.flag(3, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.flag(-1, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.flag(0, -1);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
    }    
    
    @Test
    public void testDeFlag() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.flag(0, 0);
        assertEquals("F--", board.look()[0]);
        board.deflag(0, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
    }
    
    @Test
    public void testInvalidDeFlag() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.deflag(0, 3);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.deflag(3, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.deflag(-1, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.deflag(0, -1);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
    }    
    
    @Test
    public void testDig() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.dig(0, 0);
        assertEquals("1--", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(1, 0);
        assertEquals("11-", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(2, 0);
        assertEquals("11 ", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(1, 1);
        assertEquals("11 ", board.look()[0]);
        assertEquals("-1-", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(2, 1);
        assertEquals("11 ", board.look()[0]);
        assertEquals("-1 ", board.look()[1]);
        assertEquals("---", board.look()[2]);
        
        board.dig(0, 2);
        assertEquals("11 ", board.look()[0]);
        assertEquals("-1 ", board.look()[1]);
        assertEquals("1--", board.look()[2]); 
        
        board.dig(1, 2);
        assertEquals("11 ", board.look()[0]);
        assertEquals("-1 ", board.look()[1]);
        assertEquals("11-", board.look()[2]); 
        
        board.dig(2, 2);
        assertEquals("11 ", board.look()[0]);
        assertEquals("-1 ", board.look()[1]);
        assertEquals("11 ", board.look()[2]); 
        
        assertEquals("BOOM!", board.dig(0, 1)[0]);
    }
    
    @Test
    public void testDigBoom() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.dig(0, 0);
        assertEquals("1--", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(1, 0);
        assertEquals("11-", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        assertEquals("BOOM!", board.dig(0, 1)[0]);
        assertEquals("  -", board.look()[0]);
        assertEquals(" --", board.look()[1]);
        assertEquals("---", board.look()[2]);
        
        board.dig(1, 2);
        assertEquals("  -", board.look()[0]);
        assertEquals(" --", board.look()[1]);
        assertEquals("- -", board.look()[2]);
        
    }    
    
    @Test
    public void testInvalidDig() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.dig(0, 3);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(3, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(-1, 0);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(0, -1);
        assertEquals("---", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
    }    
    
    @Test
    public void testDigTouched() {  
        // Set bomb=true in SquareBoard.java
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        board.dig(0, 0);
        assertEquals("1--", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
        
        board.dig(0, 0);
        assertEquals("1--", board.look()[0]);
        assertEquals("---", board.look()[1]);
        assertEquals("---", board.look()[2]); 
    }    
    
    @Test
    public void testPrettyBoard() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        for (String str: board.look())
            System.out.println(str);

    }
    
    @Test
    public void testAddRemovePlayer() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        assertEquals("1", board.addPlayer());        
        assertEquals("2", board.addPlayer());
        
        board.removePlayer();
        assertEquals("2", board.addPlayer()); 
    }
    
    @Test
    public void testGetNumPlayers() {  
        
        List<Integer> params = new ArrayList<Integer>();
        params.add(3);
        params.add(3);
        params.add(3); // Add bomb at position 3
        
        Board board = new SquareBoard(params, true);
        assertEquals("1", board.addPlayer());        
        assertEquals("2", board.addPlayer());
        
        assertEquals(2, board.getNumPlayers());
        
        board.removePlayer();
        assertEquals(1, board.getNumPlayers()); 
    }

}
