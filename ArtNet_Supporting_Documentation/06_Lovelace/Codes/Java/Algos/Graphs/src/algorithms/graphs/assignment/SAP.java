package algorithms.graphs.assignment;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    
    private Digraph copyG;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        
        if (G == null)
            throw new NullPointerException("Graph is null");
        
        copyG = new Digraph(G);
        
    } // Constructor end
    
    

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        // Validate method parameters
        validateVertex(v);
        validateVertex(w);
        
        // Get shortest ancestor and corresponding min. distance
        int[] results = getAncestors(v, w);
        
        // return the minimum distance
        if (results[1] == -1)  
            return -1;
        else
            return results[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        // validate method parameters
        validateVertex(v);
        validateVertex(w);
        
        // Get shortest ancestor and corresponding min. distance
        int[] results = getAncestors(v, w);
        
        // return shortest ancestor
        return results[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // validate parameters
        if (v == null || w == null)
            throw new NullPointerException("Iterables are null");
        
        for (int vertex: v) 
            validateVertex(vertex);     
        for (int wertex: w) 
            validateVertex(wertex); 
        
        // Get shortest ancestor and corresponding min. distance
        int[] results = getAncestors(v, w);
        
        // return the minimum distance
        if (results[1] == -1)  
            return -1;
        else
            return results[0];    
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // validate parameters
        if (v == null || w == null)
            throw new NullPointerException("Iterables are null");
        
        for (int vertex: v) 
            validateVertex(vertex);      
        for (int wertex: w) 
            validateVertex(wertex); 
        
        // Get shortest ancestor and corresponding min. distance
        int[] results = getAncestors(v, w);
        // return shortest ancestor
        return results[1];
    }
    
    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
        if (v < 0 || v >= copyG.V())
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (copyG.V()-1));
    }
    
    // Helper Function: Returns shortest Ancestor and shortest distance of two vertices in Graph
    private int[] getAncestors(int v, int w) {
        
        // Carry out a BFS for each parameter v and w
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(copyG, w);
        
        return getResults(vPath, wPath);
    }
    
    // Helper Function: Returns shortest Ancestor and shortest distance of two Iterables in Graph
    private int[] getAncestors(Iterable<Integer> v, Iterable<Integer> w) {
        
        // Carry out a BFS for each parameter v and w
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(copyG, w);
        
        return getResults(vPath, wPath);
        
    }
    
    // helper Function: Find shortest path
    private int[] getResults(BreadthFirstDirectedPaths vPath, BreadthFirstDirectedPaths wPath) {
        
        SET<Integer> vSet = new SET<Integer>();
        SET<Integer> wSet = new SET<Integer>();
        
        // Get all vertices in graph which in the path to any v
        for (int vertex = 0; vertex < copyG.V(); vertex++) {
            if (vPath.hasPathTo(vertex)) {
                for (int vv: vPath.pathTo(vertex)) {
                    vSet.add(vv);
                }   
            }    
        }
        
        // Get all vertices in graph which in the path to any w
        for (int wertex = 0; wertex < copyG.V(); wertex++) {
            if (wPath.hasPathTo(wertex)) {
                for (int ww: wPath.pathTo(wertex)) {
                    wSet.add(ww); 
                }          
            }           
        }
        // Get the set of vertices that connect to both v and w
        SET<Integer> intersect = vSet.intersects(wSet);
        
        // Find the shortest Ancestor and corresponding distance
        int minDist = 1000000000;
        int shortAncestor = -1;
        int testDist = 0;
        for (int ancestor: intersect) {
            testDist = vPath.distTo(ancestor) + wPath.distTo(ancestor);
            if (testDist < minDist) {
               minDist = testDist;
               shortAncestor = ancestor;
            }
        }
        int[] results = new int[2];
        results[0] = minDist;
        results[1] = shortAncestor;
        return results;
    }

    public static void main(String[] args) {
        
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println("digraph");
        SAP sap = new SAP(G);
        StdOut.println("sap");
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }

}
