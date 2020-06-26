/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   Commands.differentiate() 
    //     Plus
    //     Sum
    //     Combination
    // Commands.simplify()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //  Commands.differentiate() 
    @Test
    public void testDifferentiateTimes() {
        String output1 = Commands.differentiate("x*x", "x");
        assertEquals(output1, "x*1+x*1");
        
        String output2 = Commands.differentiate("y*y", "y");
        assertEquals(output2, "y*1+y*1");
        
        String output3 = Commands.differentiate("x", "x");
        assertEquals(output3, "1");
        
        String output4 = Commands.differentiate("x*x*x", "x");
        System.out.println(output4);
        //assertEquals(output4, "x*1+x*1");
        
    }
    
    @Test
    public void testDifferentiatePlus() {
        String output1 = Commands.differentiate("x+x", "x");
        assertEquals(output1, "1+1");
        
        String output2 = Commands.differentiate("y+y", "y");
        assertEquals(output2, "1+1");
        
        String output3 = Commands.differentiate("x", "x");
        assertEquals(output3, "1");
        
    }
    
    @Test
    public void testDifferentiateCombination() {
        String output1 = Commands.differentiate("x*x+x", "x");
        assertEquals(output1, "x*1+x*1+1");
    }
    
    // Commands.simplify()
    @Test
    public void testSimplifyTimes() {
        final Map<String,Double> environment = new HashMap<String, Double>();
        environment.put("x", 1.0);
        environment.put("y", 2.0);
        environment.put("z", 3.0);
        
        String output1 = Commands.simplify("x", environment);
        assertEquals(output1, "1.0");
        
        String output2 = Commands.simplify("z* y", environment);
        assertEquals(output2, "6.0");
        
        String output3 = Commands.simplify("a*a*z", environment);
        assertEquals(output3, "a*a*3.0");
        
    }
    
    @Test
    public void testSimplifyPlus() {
        final Map<String,Double> environment = new HashMap<String, Double>();
        environment.put("x", 1.0);
        environment.put("y", 2.0);
        environment.put("z", 3.0);
        
        String output1 = Commands.simplify("x+y", environment);
        assertEquals(output1, "3.0");
        
        String output2 = Commands.simplify("x+ a", environment);
        assertEquals(output2, "1.0+a");
        
        String output3 = Commands.simplify("a+a+z", environment);
        assertEquals(output3, "a+a+3.0");
        
    }
    
    @Test
    public void testSimplifyCombo() {
        final Map<String,Double> environment = new HashMap<String, Double>();
        environment.put("x", 1.0);
        environment.put("y", 2.0);
        environment.put("z", 3.0);
        
        String output1 = Commands.simplify("a+a*z", environment);
        assertEquals(output1, "a+a*3.0");
        
        String output2 = Commands.simplify("a*a+z", environment);
        assertEquals(output2, "a*a+3.0");
        
    }
    
    @Test
    public void testSimplifyMathExpression() {
        final Map<String,Double> environment = new HashMap<String, Double>();
        environment.put("x", 5.0);
        environment.put("y", 10.0);
        environment.put("z", 20.0);
        
        final Map<String,Double> environment2 = new HashMap<String, Double>();
        environment2.put("y", 10.0);
        
        String output1 = Commands.simplify("x*x*x", environment);
        assertEquals(output1, "125.0");
        
        String output2 = Commands.simplify("x*x*x + y*y*y", environment2);
        assertEquals(output2, "x*x*x+1000.0");
        
        final Map<String,Double> environment3 = new HashMap<String, Double>();
        String output3 = Commands.simplify("1+2*3+8*0.5", environment3);
        assertEquals(output3, "11.0");
        
        final Map<String,Double> environment4 = new HashMap<String, Double>();
        environment4.put("x", 2.0);
        String output4 = Commands.simplify("x*x*y + y*(1+x)", environment4);
        assertEquals(output4, "4.0*y+y*3.0");
                
        
        String output5 = Commands.simplify("y+z", environment);
        assertEquals(output5, "30.0");
        
        String output6 = Commands.simplify("(y+z) + a*b", environment);
        assertEquals(output6, "30.0+a*b");
        
    }
    
    
}
