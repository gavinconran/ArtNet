package union.questions;

import union.find.UF.UF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Question1 {

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
    		StdOut.println("N: " + N);
    		UF uf = new UF(N);
    		while (!in.isEmpty()) {	
    			int p = in.readInt();
    			int q = in.readInt();
    			if (uf.connected(p, q)) continue;
    			uf.union(p, q);
    			StdOut.println(p + " " + q);
    		}
    		StdOut.println(uf.count() + " components");
    		int[] parents = uf.parent();
    		for (int p = 0; p < parents.length; p++){
    			StdOut.println(parents[p] + " parent");
    			
    		}
    	}
        catch (Exception e) 
    	{
            e.printStackTrace();        
        }
    }

}
