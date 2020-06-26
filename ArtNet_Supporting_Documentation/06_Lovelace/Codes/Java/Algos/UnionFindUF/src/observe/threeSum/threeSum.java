package observe.threeSum;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.ThreeSumFast;

public class threeSum {
	
	public static int count(int[] a){
		int N = a.length;
		int count = 0;
		for (int i = 0; i < N; i++)
			for (int j = i+1; j < N; j++)
				for (int k = j+1; k < N; k++)
					if (a[i] + a[j] + a[k] == 0)
						count++;
		return count;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = In.readInts(args[0]);
		
		Stopwatch stopwatch = new Stopwatch();
		StdOut.println("Count: " + ThreeSumFast.count(a));
		double time = stopwatch.elapsedTime();
		
		StdOut.println("Time: " + time);

	}

}
