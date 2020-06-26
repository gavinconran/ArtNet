/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import lib6005.parser.UnableToParseException;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // Concrete variant Term tests
    // Concrete variant Plus tests
    // Concrete variant Times tests
    // Test Term Plus Times Combinations
    
    // Test parse
    //    valid input
    //    invalid input - check for IllegalArgumentException
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // Tests for Expression
    
    // Tests for Term
    @Test
    public void testNewTermExpression() {
        Expression term = Expression.term("x");
        assertEquals(term.toString(), "x");
    }
    
    @Test
    public void testTermString() {  
        
        Expression exprInt0 = new Term("0");
        assertEquals("0", exprInt0.toString());
        
        Expression exprDouble0 = new Term("0.0");
        assertEquals("0.0", exprDouble0.toString());
        
        Expression exprInt = new Term("x");
        assertEquals("x", exprInt.toString());
        
        Expression exprDouble = new Term("1.1");
        assertEquals("1.1", exprDouble.toString());
    }
    
    @Test
    public void testTermHashCode() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("x");        
        assertEquals(term1.hashCode(), term2.hashCode());
    }
    
    @Test
    public void testTermEquals() {
        Expression term1 = Expression.term("xyz");
        Expression term2 = Expression.term("xyz");        
        assertTrue(term1.equals(term2));
    }
    
    @Test
    public void testStructuralEquality() {  
        Expression test1 = new Term("(1 + x)");
        Expression test2 = new Term("(x + 1)");
        assertFalse(test1.equals(test2));
        
        Expression test3 = new Term("(1 + x)");
        assertTrue(test1.equals(test3));
    }    
    
    // Tests for Plus
    @Test
    public void testNewPlusExpression() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("y");
        
        Expression plus1 = new Plus(term1, term2);
        assertEquals(plus1.toString(), "x+y");
    }
    
    @Test
    public void testPlusExpressionHashCode() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("a");        
        
        Expression plus1 = new Plus(term1, term2);
        Expression plus2 = new Plus(term2, term1);
        assertEquals(plus1.hashCode(), plus2.hashCode());
    }
    
    @Test
    public void testPlusExpressionEquals() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("x");    
        Expression plus1 = new Plus(term1, term2);
        
        Expression term3 = Expression.term("x");
        Expression term4 = Expression.term("x");    
        Expression plus2 = new Plus(term3, term4);
        
        assertTrue(plus1.equals(plus2));
    }
    
    // Tests for Times
    @Test
    public void testNewTimesExpression() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("y");
        
        Expression times1 = new Times(term1, term2); 
        assertEquals(times1.toString(), "x*y");
        
        Expression times2 = new Times(term2, term1); 
        
        Expression times3 = new Times(times1, times2); 
        assertEquals(times3.toString(), "x*y*y*x");
    }
    
    @Test
    public void testTimesExpressionHashCode() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("a");        
        
        Expression times1 = new Times(term1, term2);
        Expression times2 = new Times(term2, term1);
        
        assertEquals(times1.hashCode(), times2.hashCode());
    }
    
    @Test
    public void testTimesExpressionEquals() {
        Expression term1 = Expression.term("x");
        Expression term2 = Expression.term("x");    
        Expression times1 = new Times(term1, term2);
        
        Expression term3 = Expression.term("x");
        Expression term4 = Expression.term("x");    
        Expression times2 = new Times(term3, term4);
        
        assertTrue(times1.equals(times2));
    }
    
    // Test Combinations
    @Test
    public void testTermPlusExpTimesExp1() {  
        Expression term0 = new Term("x");
        Expression number1 = new Term("1.1");
        Expression number2 = new Term("12");
        Expression expr1 = new Times(term0, number1);
        Expression expr2 = new Times(term0, number1);
        assertTrue(expr1.equals(expr2));
        assertTrue(expr1.equals(expr1));
        assertTrue(expr2.equals(expr2));
        
        Expression expr3 = new Times(term0, number2);
        assertFalse(expr1.equals(expr3));
        Expression expr4 = new Plus(expr3, expr2);
        assertFalse(expr1.equals(expr4));
    }
    
    // Test parser
    @Test
    public void testParservalidInput() throws UnableToParseException, IOException{
        Expression exp1 = Expression.parse("4 + 3 * x + 2 * x * x + 1 * x * x * (((x)))");
        assertEquals(exp1.toString(), "4+3*x+2*x*x+1*x*x*x");
        
        Expression exp2 = Expression.parse("3 + 2.4");
        assertEquals(exp2.toString(), "3+2.4");
        
        Expression exp3 = Expression.parse("3 * (x + 2.4)");
        assertEquals(exp3.toString(), "3*x+2.4");
        
        Expression exp4 = Expression.parse("AbC + (123+x)");
        assertEquals(exp4.toString(), "AbC+123+x");
        
        Expression exp5 = Expression.parse("((3 + 4) * x * x)");
        assertEquals(exp5.toString(), "3+4*x*x");
        
        Expression exp6 = Expression.parse("3 * x + 2.4");
        assertEquals(exp6.toString(), "3*x+2.4");
        
        Expression exp7 = Expression.parse("foo + bar+baz");
        assertEquals(exp7.toString(), "foo+bar+baz");
        
        Expression exp8 = Expression.parse("(2*x    )+    (    y*x    )");
        assertEquals(exp8.toString(), "2*x+y*x");
    }
    
    // Test parser
    @Test(expected=IllegalArgumentException.class)
    public void testParserInvalidInput1() {
        Expression.parse("3xy");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testParserInvalidInput2() {
        Expression.parse("( 3");
    }  
    
    @Test(expected=IllegalArgumentException.class)
    public void testParserInvalidInput3() {
        Expression.parse("3x");
    }  
    
}
