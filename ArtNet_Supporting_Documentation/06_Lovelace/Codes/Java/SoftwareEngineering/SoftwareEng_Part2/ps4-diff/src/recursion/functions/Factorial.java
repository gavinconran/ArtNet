package recursion.functions;

public class Factorial {
    
    public static long factorialIterative( int n) {
        
        long fact = 1;
        for ( int i = 1; i <= n; i++ ) {
            fact = fact * i;
        }
        return fact;
    }
    
    public static long factorialRecursive( int n ) {
        
        // base case
        if (n == 0) {
            return 1;
        } else { // recursive step
            return n * factorialRecursive( n - 1);
        }
    }

}
