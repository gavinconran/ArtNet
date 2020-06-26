package monte.carlo.percolation;

import java.util.Random;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] allP; // Array of the results of p from all the tests
    private double times; // number of times the tests are to be carried out

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {

        allP = new double[T];
        times = (double) T;
        
        // Make sure N and T are valid, i.e. greater then 0
        if (N > 0 && T > 0) {
            // Run the test T times
            for (int t = 0; t < T; t++) {
                // seed a new random number generator
                Random rand = new Random();
                // create a new count variable for test t
                int count = 0;
                // Create a new instance of Percolation for test t
                Percolation perc = new Percolation(N);
                // while the tests Percolation does not percolaute
                while (!perc.percolates()) {
                    // randomly select a site
                    int p = rand.nextInt(N) + 1;
                    int q = rand.nextInt(N) + 1;
                    if (!perc.isOpen(p, q)) {
                        perc.open(p, q);
                        count++;
                    }			
                }
                // add the result of test t to stats.allP array
                allP[t] = (double) count / (double) (N * N);			
            }
        } else // N and/or T are out of bounds
        	throw new IllegalArgumentException("One or both of N and T are out of bounds");       
 
    } // End PercolationStats

    // Return the mean of all p values
    public double mean() {

    	double sumP = 0;		
        for (double p: allP) {
            sumP += p;			
        }
        return sumP / times;
    }

    // Return the standard deviation of all p values
    public double stddev() {

    	double meanP = mean();
        double diffP = 0;
        for (double p: allP) {
            diffP += (p - meanP) * (p - meanP);			
        }
        // standard deviation is the square root of the variance
        return Math.sqrt(diffP / (times - 1));
    }

    // Return the 95% low confidence level
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(times));
    }

    // Return the 95% high confidence level
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(times));
    }

    public static void main(String[] args) {

        // Obtain N and T
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
   
        // Create a new instance of PercolationStats
        PercolationStats stats = new PercolationStats(N, T);

        // Print out the mean, standard deviation & 95% confidence levels of p* for the all tests T
        StdOut.println("mean =  " + stats.mean());
        StdOut.println("stddev =  " + stats.stddev());
        StdOut.println("95% Low confidence interval =  " + stats.confidenceLo());
        StdOut.println("95% High confidence interval =  " + stats.confidenceHi());

    } // end main
} // end class
