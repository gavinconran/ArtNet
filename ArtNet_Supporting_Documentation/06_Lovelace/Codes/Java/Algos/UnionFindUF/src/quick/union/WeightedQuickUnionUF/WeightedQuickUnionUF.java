package quick.union.WeightedQuickUnionUF;

import union.find.UF.UF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/******************************************************************************
 *  Compilation:  javac WeightedQuickUnionUF.java
 *  Execution:  java WeightedQuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Weighted quick-union (without path compression).
 *
 ******************************************************************************/

/**
 *  The <tt>WeightedQuickUnionUF</tt> class represents a union-find data structure.
 *  It supports the <em>union</em> and <em>find</em> operations, along with
 *  methods for determinig whether two objects are in the same component
 *  and the total number of components.
 *  <p>
 *  This implementation uses weighted quick union by size (without path compression).
 *  Initializing a data structure with <em>N</em> objects takes linear time.
 *  Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 *  logarithmic time (in the worst case) and <em>count</em> takes constant
 *  time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *     
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of objects in subtree rooted at i
    private int count;      // number of components

    /**
     * Initializes an empty union-find data structure with N isolated components 0 through N-1.
     * @param N the number of objects
     * @throws java.lang.IllegalArgumentException if N < 0
     */
    public WeightedQuickUnionUF(int N) {
        count = N;
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     * @return the number of components (between 1 and N)
     */
    public int count() {
        return count;
    }
    
    public int[] parent() {
        return parent;
    }

    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     * @param p the integer representing one site
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
        	// Add extra line for path compression
        	//parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N);
        }
    }

    /**
     * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt>
     *    are in the same component, and <tt>false</tt> otherwise
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

  
    /**
     * Merges the component containing site<tt>p</tt> with the component
     * containing site <tt>q</tt>.
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }


    /**
     * Reads in a sequence of pairs of integers (between 0 and N-1) from standard input, 
     * where each integer represents some object;
     * if the objects are in different components, merge the two components
     * and print the pair to standard output.
     */
    public static void main(String[] args) {
    	String filename;
    	In in;
    	
    	for (String s: args) {
            System.out.println(s);
        }
    	try 
    	{
    		filename = args[0];
    		in = new In(filename);
    		int N = in.readInt();
    		UF uf = new UF(N);
    		while (!in.isEmpty()) {	
    			int p = in.readInt();
    			int q = in.readInt();
    			if (uf.connected(p, q)) continue;
    			uf.union(p, q);
    			StdOut.println(p + " " + q);
    		}
    		StdOut.println(uf.count() + " components");
    	}
        catch (Exception e) 
    	{
            e.printStackTrace();        
        }
    }

}

