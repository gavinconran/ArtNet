package recursion.functions;

import static org.junit.Assert.*;

import org.junit.Test;

import recursion.functions.Factorial;

public class FactorialTest {

    @Test
    public void testfactorialIterative() {
        assertEquals(1, Factorial.factorialIterative(0));
        assertEquals(1, Factorial.factorialIterative(1));
        assertEquals(2, Factorial.factorialIterative(2));
        assertEquals(6, Factorial.factorialIterative(3));
        assertEquals(24, Factorial.factorialIterative(4));
    }
    
    @Test
    public void testfactorialRecursive() {
        assertEquals(1, Factorial.factorialRecursive(0));
        assertEquals(1, Factorial.factorialRecursive(1));
        assertEquals(2, Factorial.factorialRecursive(2));
        assertEquals(6, Factorial.factorialRecursive(3));
        assertEquals(24, Factorial.factorialIterative(4));
    }

}
