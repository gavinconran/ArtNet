package recursion.functions;

import static org.junit.Assert.*;

import org.junit.Test;

import recursion.functions.Fibonacci;

public class FibonacciTest {

    @Test
    public void testFibonacci() {
        assertEquals(1, Fibonacci.fibonacci(0));
        assertEquals(1, Fibonacci.fibonacci(1));
        assertEquals(2, Fibonacci.fibonacci(2));
        assertEquals(3, Fibonacci.fibonacci(3));
        assertEquals(5, Fibonacci.fibonacci(4));
        assertEquals(8, Fibonacci.fibonacci(5));
        assertEquals(13, Fibonacci.fibonacci(6));
    }
}
