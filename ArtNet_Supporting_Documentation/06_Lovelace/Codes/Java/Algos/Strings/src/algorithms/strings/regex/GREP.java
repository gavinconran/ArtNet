package algorithms.strings.regex;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Compilation:  javac GREP.java
 *  Execution:    java GREP regexp < input.txt
 *  Dependencies: NFA.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/54regexp/tinyL.txt
 *
 *  This program takes an RE as a command-line argument and prints
 *  the lines from standard input having some substring that
 *  is in the language described by the RE. 
 *
 *  % more tinyL.txt
 *  AC
 *  AD
 *  AAA
 *  ABD
 *  ADD
 *  BCD
 *  ABCCBD
 *  BABAAA
 *  BABBAAA
 *
 *  %  java GREP "(A*B|AC)D" < tinyL.txt
 *  ABD
 *  ABCCBD
 *
 ******************************************************************************/

/**
 *  The <tt>GREP</tt> class provides a client for reading in a sequence of
 *  lines from standard input and printing to standard output those lines
 *  that contain a substring matching a specified regular expression.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class GREP {

    // do not instantiate
    private GREP() { }

    /**
     * Interprets the command-line argument as a regular expression
     * (supporting closure, binary or, parentheses, and wildcard)
     * reads in lines from standard input; writes to standard output
     * those lines that contain a substring matching the regular
     * expression.
     */
    public static void main(String[] args) { 
        String regexp = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()) { 
            String line = StdIn.readLine();
            if (nfa.recognizes(line)) {
                StdOut.println(line);
            }
        }
    } 
} 
