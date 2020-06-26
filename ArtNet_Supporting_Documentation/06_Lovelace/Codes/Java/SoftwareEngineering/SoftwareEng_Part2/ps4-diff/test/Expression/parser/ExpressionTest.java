package Expression.parser;

import static org.junit.Assert.*;
import java.util.Map;

import org.junit.Test;

public class ExpressionTest {

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
    public void testPlusExp1() {  
        Expression number0 = new Term("0");
        Expression number1 = new Term("1.1");
        Expression expr1 = new PlusExp(number0, number1);
        Expression expr2 = new PlusExp(number0, number1);
        assertTrue(expr1.equals(expr2));
        
        Expression expr3 = new PlusExp(number1, number1);
        assertFalse(expr1.equals(expr3));
    }
    
    @Test
    public void testTimesExp1() {  
        Expression number0 = new Term("0");
        Expression number1 = new Term("1.1");
        Expression expr1 = new TimesExp(number0, number1);
        Expression expr2 = new TimesExp(number0, number1);
        assertTrue(expr1.equals(expr2));
        assertTrue(expr1.equals(expr1));
        assertTrue(expr2.equals(expr2));
        
        Expression expr3 = new TimesExp(number1, number1);
        assertFalse(expr1.equals(expr3));
    }
    
    @Test
    public void testTermPlusExpTimesExp1() {  
        Expression term0 = new Term("x");
        Expression number1 = new Term("1.1");
        Expression number2 = new Term("12");
        Expression expr1 = new TimesExp(term0, number1);
        Expression expr2 = new TimesExp(term0, number1);
        assertTrue(expr1.equals(expr2));
        assertTrue(expr1.equals(expr1));
        assertTrue(expr2.equals(expr2));
        
        Expression expr3 = new TimesExp(term0, number2);
        assertFalse(expr1.equals(expr3));
        Expression expr4 = new PlusExp(expr3, expr2);
        assertFalse(expr1.equals(expr4));
    }
    
    @Test
    public void testStructuralEquality() {  
        Expression test1 = new Term("(1 + x)");
        Expression test2 = new Term("(x + 1)");
        assertFalse(test1.equals(test2));
        
        Expression test3 = new Term("(1 + x)");
        assertTrue(test1.equals(test3));
    }
    
    @Test
    public void testFromCourse1() {  
        Expression test1 = new Term("((3 + 4) * x * x)");
        Expression test2 = new Term("((3+ 4) *x * x)");
        assertTrue(test1.parse().equals(test2.parse()));
        
        Expression test3 = new Term("Foo + bar+baz");
        assertFalse(test1.parse().equals(test3.parse()));
    }
    
    @Test
    public void testFromCourse2() {  
        Expression test1 = new Term("3 + 2.4");
        assertTrue("3+2.4".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse3() {  
        Expression test1 = new Term("3 * x + 2.4");
        assertTrue("3*(x)+2.4".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse4() {  
        Expression test1 = new Term("3 * (x + 2.4)");
        assertTrue("3*(x+2.4)".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse5() {  
        Expression test1 = new Term("((3 + 4) * x * x)");
        assertTrue("3+4*(x)*(x)".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse6() {  
        Expression test1 = new Term("Foo + bar+baz");
        assertTrue("Foo+bar+baz".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse7() {  
        Expression test1 = new Term("(2*x    )+    (    y*x    )");
        assertTrue("2*(x)+y*(x)".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse8() {  
        Expression test1 = new Term("4 + 3 * x + 2 * x * x + 1 * x * x * (((x)))");
        assertTrue("4+3*(x)+2*(x)*(x)+1*(x)*(x)*(x)".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse9() {  
        Expression test1 = new Term("3 *");
        assertTrue("Invalid Input".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse10() {  
        Expression test1 = new Term("( 3");
        assertTrue("Invalid Input".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse11() {  
        Expression test1 = new Term("3 x");
        assertTrue("Invalid Input".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse12() {  
        Expression test1 = new Term("(2*  x    )+    (    y*x    )");
        assertTrue("2*(x)+y*(x)".equals(test1.parse().toString()));
    }
 
    @Test
    public void testFromCourse13() {  
        Expression test1 = new Term("2 - 3");
        assertTrue("Invalid Input".equals(test1.parse().toString()));
    }
    
    @Test
    public void testFromCourse14() {  
        Expression expression = new Term("1");
        Expression parsedExp = expression.parse();
        
        // Extract w.r.t variable from command input
        String command = "!d/dx";
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        
        assertEquals("0", diffExp.toString());
    }
    
    @Test
    public void testFromCourse15() {  
        Expression expression = new Term("x");
        Expression parsedExp = expression.parse();
        
        // Extract w.r.t variable from command input
        String command = "!d/dx";
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        
        assertEquals("1", diffExp.toString());
    }
    
    @Test
    public void testFromCourse16() {  
        Expression expression = new Term("x + y");
        Expression parsedExp = expression.parse();
        
        // Extract w.r.t variable from command input
        String command = "!d/dx";
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        
        assertEquals("1+0", diffExp.toString());
    }
    
    @Test
    public void testFromCourse17() {  
        Expression expression = new Term("x *x + y + y");
        Expression parsedExp = expression.parse();
        
        // Extract w.r.t variable from command input
        String command = "!d/dx";
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        
        assertEquals("x*(1)+x*(1)+0+0", diffExp.toString());
    }
    
    @Test
    public void testFromCourse18() {  
        Expression expression = new Term("x *x*x");
        Expression parsedExp = expression.parse();
        
        // Extract w.r.t variable from command input
        String command = "!d/dx";
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        
        assertEquals("x*(x)*(1)+x*(x*(1)+x*(1))", diffExp.toString());
    }
    
    @Test
    public void testTimesExp2() {  
        Expression left = new Term("x");
        Expression right = new Term("(x*1) + (x*1)");
        Expression timesExp = new TimesExp(left, right);
        assertEquals("x*(x*(1)+x*(1))", timesExp.parse().toString());   
    }
    
    @Test
    public void testDiff1() {  
        String input = "5*x";
        String command = "!d/dx";
        
        // Create a parsed expression from input
        Expression expression = new Term(input);
        Expression parsedExp = expression.parse();
        assertEquals("5*(x)", parsedExp.toString());
        
        String wrtVar = Regex.regexDiff(command);
        Expression diffExp = parsedExp.differentiate(wrtVar);
        assertEquals("5*(1)+x*(0)", diffExp.toString());
    }
    
    @Test
    public void testSimplify1() { 
        String input = "x";
        String command = "!simplify x=2";
        
        // Create a parsed expression from input
        Expression expression = new Term(input);
        Expression parsedExp = expression.parse();
        assertEquals("x", parsedExp.toString());
        
        // Extract variable value pairs form command input
        Map<String, Double> Vars = Regex.regexSimp(command); 
        
        Expression simpExp = expression.simplify(Vars);
        assertEquals("2.0", simpExp.toString());
        String environment = Vars.toString().substring(1, Vars.toString().length() - 1);
        assertEquals("x=2.0", environment);
    }
    
    @Test
    public void testSimplify2() { 
        String input = "x * x";
        String command = "!simplify x=2";
        
        // Create a parsed expression from input
        Expression expression = new Term(input);
        Expression parsedExp = expression.parse();
        assertEquals("x*(x)", parsedExp.toString());
        
        // Extract variable value pairs form command input
        Map<String, Double> Vars = Regex.regexSimp(command); 

        Expression simpExp = parsedExp.simplify(Vars);
        assertEquals("2.0*(2.0)", simpExp.toString());
        String environment = Vars.toString().substring(1, Vars.toString().length() - 1);
        assertEquals("x=2.0", environment);
    }
    
    @Test
    public void testSimplify3() { 
        String input = "x * x+y";
        String command = "!simplify x=2 z=1";
        
        // Create a parsed expression from input
        Expression expression = new Term(input);
        Expression parsedExp = expression.parse();
        assertEquals("x*(x)+y", parsedExp.toString());
        
        // Extract variable value pairs form command input
        Map<String, Double> Vars = Regex.regexSimp(command); 
        
        Expression simpExp = parsedExp.simplify(Vars);
        assertEquals("2.0*(2.0)+y", simpExp.toString());
        String environment = Vars.toString().substring(1, Vars.toString().length() - 1);
        assertEquals("x=2.0, z=1.0", environment);
    }
    
    @Test
    public void testSimplify4() { 
        String input = "x * x+y";
        String command = "!simplify x=2 y=1";
        
        // Create a parsed expression from input
        Expression expression = new Term(input);
        Expression parsedExp = expression.parse();
        assertEquals("x*(x)+y", parsedExp.toString());
        
        // Extract variable value pairs form command input
        Map<String, Double> Vars = Regex.regexSimp(command); 

        Expression simpExp = parsedExp.simplify(Vars);
        assertEquals("2.0*(2.0)+1.0", simpExp.toString());
        String environment = Vars.toString().substring(1, Vars.toString().length() - 1);
        assertEquals("x=2.0, y=1.0", environment);
    }
    
}
