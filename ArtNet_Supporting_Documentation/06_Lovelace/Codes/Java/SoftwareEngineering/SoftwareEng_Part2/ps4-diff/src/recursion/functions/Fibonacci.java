package recursion.functions;

public class Fibonacci {
    
    /**
     * @param n >= 0
     * @return the nth Fibonacci number 
     */
    public static int fibonacci(int n) {
        if (n == 0 || n == 1) {  // 2 base cases
            return 1; // base cases
        } else { // recursive step
            return fibonacci(n-1) + fibonacci(n-2); // recursive step
        }
    }
}
