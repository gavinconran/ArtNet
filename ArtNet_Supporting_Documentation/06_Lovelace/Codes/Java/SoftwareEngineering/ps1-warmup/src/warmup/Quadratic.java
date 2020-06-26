package warmup;

import java.util.HashSet;
import java.util.Set;


public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) { 
        
        
        final Set<Integer> rootSet = new HashSet<Integer>(); // Set of computed roots
        double root1;
        double root2;
        
        if ( a==0 && b == 0 && c == 0 ) { // cannot have a=b=c=0
            return rootSet;
        }
        
        if ( a == 0) { // linear equation
            if ( b == 0 ) {
                root1 = c;
                rootSet.add((int) root1);
            } else {
                root1 = -c / b;
                rootSet.add((int) root1);
            }
        } else if ( b*b > 4*a*c ) { // two real roots
            root1 = (-b + Math.sqrt(b*b - 4*a*c)) / (2*a);
            root2 = (-b - Math.sqrt(b*b - 4*a*c)) / (2*a);
            rootSet.add((int) root1);
            rootSet.add((int) root2);
        } else if (b*b == 4*a*c) { // single recurring root
            root1 = (int) (-b / (2*a));
            rootSet.add((int) root1);
        } else { // large roots
            root1 = -1 * Math.sqrt(Math.abs(c)); 
            root2 = Math.sqrt(Math.abs(c));
            rootSet.add((int) root1);
            rootSet.add((int) root2);
        }
                
        return rootSet;
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 3);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
