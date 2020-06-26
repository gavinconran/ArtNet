package algorithms.strings.compression;

import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;


/******************************************************************************
 *  Compilation:  javac Genome.java
 *  Execution:    java Genome - < input.txt   (compress)
 *  Execution:    java Genome + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 *
 *  % more genomeTiny.txt
 *  ATAGATGCATAGCGCATAGCTAGATGTGCTAGC
 *
 *  % java Genome - < genomeTiny.txt | java Genome +
 *  ATAGATGCATAGCGCATAGCTAGATGTGCTAGC
 *
 ******************************************************************************/

/**
 *  The <tt>Genome</tt> class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Genome {
    private static final Alphabet DNA = new Alphabet("ACGT");

    // Do not instantiate.
    private Genome() { }

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses them using two bits per
     * character; and writes the results to standard output.
     */
    public static void compress() { 
        String s = "ATAGATGCATAGCGCATAGCTAGATGTGCTAGC"; //BinaryStdIn.readString();
        int N = s.length();
//        BinaryStdOut.write(N);
        StdOut.println(N);

        // Write two-bit code for char. 
        for (int i = 0; i < N; i++) {
            int d = DNA.toIndex(s.charAt(i));
//            BinaryStdOut.write(d, 2);
            StdOut.println("DNA: " + d + " Binary: " + Integer.toBinaryString(d));
        }
        BinaryStdOut.close();
    } 

    /**
     * Reads a binary sequence from standard input; converts each two bits
     * to an 8-bit extended ASCII character over the alphabet { A, C, T, G };
     * and writes the results to standard output.
     */
    public static void expand() {
        int N = BinaryStdIn.readInt();
        // Read two bits; write char. 
        for (int i = 0; i < N; i++) {
            char c = BinaryStdIn.readChar(2);
            BinaryStdOut.write(DNA.toChar(c), 8);
        }
        BinaryStdOut.close();
    }


    /**
     * Sample client that calls <tt>compress()</tt> if the command-line
     * argument is "-" an <tt>expand()</tt> if it is "+".
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
