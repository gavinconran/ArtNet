package grammar.parser.doubleandinteger;

import static org.junit.Assert.*;

import org.junit.Test;


public class IntegerExpressionTest {

    @Test
    public void testNumberIntValue() {  
        
        IntegerExpression exprInt0 = new Number(0);
        assertEquals(0, exprInt0.value(), 0.0001);
        
        IntegerExpression exprDouble0 = new Number(0.0);
        assertEquals(0.0, exprDouble0.value(), 0.0001);
        
        IntegerExpression exprInt = new Number(1);
        assertEquals(1, exprInt.value(), 0.0001);
        
        IntegerExpression exprDouble = new Number(1.1);
        assertEquals(1.1, exprDouble.value(), 0.0001);
    }
    
    @Test
    public void testPlusIntValue1() {  
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        assertEquals(1, expr1.value(), 0.0001);    
    }
    
    @Test
    public void testPlusValue2() {  
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression expr2 = new Plus(expr1, number2);
        assertEquals(3, expr2.value(), 0.0001);
    }
    
    @Test
    public void testPlusValue3() {  
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression number3 = new Number(3);
        IntegerExpression expr2 = new Plus(number3, number2);
        
        IntegerExpression expr3 = new Plus(expr1, expr2);
        assertEquals(6, expr3.value(), 0.0001);
    }
    
    @Test
    public void testTimesValue1() {  
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Times(number0, number1);
        assertEquals(0, expr1.value(), 0.0001);    
    }
    
    @Test
    public void testTimesValue2() {  
        IntegerExpression number2 = new Number(2);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Times(number2, number1);
        assertEquals(2, expr1.value(), 0.0001);    
    }
    
    @Test
    public void testTimesPlusValue1() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression number3 = new Number(3);
        IntegerExpression expr2 = new Times(number2, number3);
        
        IntegerExpression expr3 = new Plus(expr1, expr2);
        assertEquals(7, expr3.value(), 0.0001);    
        
        IntegerExpression expr4 = new Times(expr1, expr2);
        assertEquals(6, expr4.value(), 0.0001);    
    }
    
    @Test
    public void testTimesPlusValue2() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression number3 = new Number(3);
        IntegerExpression expr2 = new Times(number2, number3);
        
        IntegerExpression expr3 = new Plus(expr1, expr2);
        
        assertNotEquals(expr2.value(), expr3.value());    
        assertFalse(expr2.equals(expr1));           
    }
    
    @Test
    public void testTimesToString() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        assertEquals("(0.0)+(1.0)", expr1.toString());
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression number3 = new Number(3);
        IntegerExpression expr2 = new Times(number2, number3);
        assertEquals("(2.0)*(3.0)", expr2.toString());
        
        IntegerExpression expr3 = new Plus(expr1, expr2);
        assertEquals("((0.0)+(1.0))+((2.0)*(3.0))", expr3.toString());
        
        IntegerExpression expr4 = new Times(expr1, expr2);
        assertEquals("((0.0)+(1.0))*((2.0)*(3.0))", expr4.toString());
    }
    
    @Test
    public void testTimesEquals1() {  
        // Immutable types are only equal when the two objects have the same reference
        IntegerExpression number0 = new Number(0);
        IntegerExpression number00 = number0;
        assertTrue(number0.equals(new Number(0))); 
        assertTrue(number0.equals(number0)); 
        assertTrue(number0.equals(number00));     
    }
    
    @Test
    public void testTimesEquals2() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(0);
        IntegerExpression number3 = new Number(1);
        IntegerExpression expr2 = new Times(number2, number3);
        
        assertFalse(expr2.equals(expr1));
        assertTrue(expr2.equals(expr2));
    }
    
    @Test
    public void testTimesEquals3() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(1);
        IntegerExpression expr1 = new Plus(number0, number1);
        
        IntegerExpression number2 = new Number(2);
        IntegerExpression number3 = new Number(3);
        IntegerExpression expr2 = new Times(number2, number3);
        
        assertFalse(expr2.equals(expr1));
    }
    
    @Test
    public void testHashCode1() {  
        
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number00 = number0;
        assertEquals(number0.hashCode(), number0.hashCode());  
        assertEquals(number00.hashCode(), number0.hashCode());  
        
        IntegerExpression number1 = new Number(0);
        
        assertEquals(number1.hashCode(), number0.hashCode()); 
        
        // The default implementation of hashCode() provided by Object is derived by mapping the memory address to an integer value. 
        // This is why the hashCodes for 
        assertEquals(527, number0.hashCode()); 
        assertEquals(527, number0.hashCode()); 
    }
    
    @Test
    public void testHashCode2() {  
        
        IntegerExpression number0 = new Number(0);
        IntegerExpression number1 = new Number(10);
        
        assertNotEquals(number1.hashCode(), number0.hashCode()); 
        
        // The default implementation of hashCode() provided by Object is derived by mapping the memory address to an integer value. 
        // This is why the hashCodes for 
        assertEquals(527, number0.hashCode()); 
        assertEquals(527, number0.hashCode()); 
    }
    
    

}
