package monte.carlo.percolation;

import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Simulation {

	public static void main(String[] args) {
		
		Random rand = new Random();
		int count = 0;
		
		// Initialise all sites to be blocked
		int N = Integer.parseInt(args[0]);
		Percolation perc = new Percolation(N);
		
		// Create stop watch
		Stopwatch stopwatch = new Stopwatch();
		
		
		// Repeat until the system percolates
		while (!perc.percolates()){
			// randomly select a site
			int p = rand.nextInt(N) + 1;
			int q = rand.nextInt(N) + 1;
			if (!perc.isOpen(p, q)){
				perc.open(p, q);
				count++;
			}			
		}
		
		double time = stopwatch.elapsedTime();
		StdOut.println("Grid size: " + N);
		StdOut.println("Time: " + time);
		StdOut.println("percolation threshold (p*): " + ((float) count / (float) (N * N)));
		
	}

}
