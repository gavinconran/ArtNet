package server;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {

    @Test
    public void testState() {  
        
        Square square = new Square(" ", false);
        assertEquals(" ", square.getState());
        
        square.changeState("-");
        assertEquals("-", square.getState());
        
        square.changeState("F");
        assertEquals("F", square.getState());
        
        square.changeState(" ");
        assertEquals(" ", square.getState());
        
        square.changeState(" ");
        assertEquals(" ", square.getState());
    }
    
    @Test
    public void testBomb() {  
        
        Square square1 = new Square(" ", true);
        assertTrue(square1.hasBomb());
        
        Square square2 = new Square(" ", false);
        assertFalse(square2.hasBomb());
    }
    
    @Test
    public void testSetBomb() {  
        
        Square square = new Square(" ", false);
        assertFalse(square.hasBomb());
        
        square.setBomb(true);;
        assertTrue(square.hasBomb());
    }

}
