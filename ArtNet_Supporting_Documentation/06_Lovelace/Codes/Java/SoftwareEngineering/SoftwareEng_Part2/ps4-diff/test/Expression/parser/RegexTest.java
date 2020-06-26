package Expression.parser;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class RegexTest {

    @Test
    public void testDiff() {
        String input = "!d/dx";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.DIFFERENTIATE, result);
    }
    
    @Test
    public void testDiff2() {
        String input = "!d/d";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.EXPRESSION, result);
    }
    
    @Test
    public void testDiff3() {
        String input1 = "!d/dx";
        String result1 = Regex.regexDiff(input1);
        assertEquals("x", result1);
        
        String input2 = "!d/dxyx";
        String result2 = Regex.regexDiff(input2);
        assertEquals("xyx", result2);
    }
    
    @Test
    public void testSimp() {
        String input = "!simplify x=10";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.SIMPLIFY, result);
    }
    
    @Test
    public void testSimp2() {
        String input = "!simplify";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.SIMPLIFY, result);
    }
    
    @Test
    public void testSimp3() {
        String input = "!simplify x=10 y=10";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.SIMPLIFY, result);
    }
    
    @Test
    public void testSimp4() {
        String input = "!simplify x=10 y=10 z=5";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.SIMPLIFY, result);
    }
    
    @Test
    public void testSimp5() {
        String input = "!simplif";
        Regex.inputType result = Regex.regex(input);
        assertEquals(Regex.inputType.EXPRESSION, result);
    }
    
    @Test
    public void testSimp6() {
        String input = "!simplify";
        Map<String, Double> result = Regex.regexSimp(input);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testSimp7() {
        String input = "!simplify x=10";
        Map<String, Double> result = Regex.regexSimp(input);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("x"));
        double value = result.get("x");
        assertEquals(10, value, 0.00001);
    }
    
    @Test
    public void testSimp8() {
        String input = "!simplify xx=10 yy=101";
        Map<String, Double> result = Regex.regexSimp(input);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("xx"));
        assertTrue(result.containsKey("yy"));
        double value = result.get("xx");
        assertEquals(10, value, 0.0001);
        
        double value2 = result.get("yy");
        assertEquals(101, value2, 0.00001);
    }
    
    @Test
    public void testSimp9() {
        String input = "!simplify xx=10.123 yy=101.1";
        Map<String, Double> result = Regex.regexSimp(input);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("xx"));
        assertTrue(result.containsKey("yy"));
        double value = result.get("xx");
        assertEquals(10.123, value, 0.0001);
        
        double value2 = result.get("yy");
        assertEquals(101.1, value2, 0.00001);
    }

}
