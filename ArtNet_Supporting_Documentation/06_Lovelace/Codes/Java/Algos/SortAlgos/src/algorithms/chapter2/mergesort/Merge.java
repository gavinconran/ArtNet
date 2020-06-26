package algorithms.chapter2.mergesort;

import algorithms.chapter2.insertion.Insertion;
import edu.princeton.cs.algs4.StdOut;

public class Merge {
    
    private final static int CUTOFF = 7; 

    public static void merge(Comparable[] a,
                             Comparable[] aux,
                             int lo,
                             int mid,
                             int hi) {
        
        assert isSorted(a, lo, mid);      // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi);    // precondition a[mid+1..hi] sorted
        
        for (int k = lo; k <= hi; k++)    // copy
            aux[k] = a[k];
        
        int i = lo, j = mid + 1;          // merge
        for (int k = lo; k <= hi; k++) {
            if          (i > mid)               a[k] = aux[j++];
            else if     (j > hi)                a[k] = aux[i++];
            else if     (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                                a[k] = aux[i++];
        }
        
        assert isSorted(a, lo, hi);     //postcondition: a[lo..hi] sorted
    }
    
    // sort top down (recursive)
    private static void sortTD(Comparable[] a,
                             Comparable[] aux,
                             int lo,
                             int hi) {
        
        // base case
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sortTD(a, aux, lo, mid);
        sortTD(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
    
    // sort top down initial method call (recursive)
    public static void sortTD(Comparable[] a) {
        
        Comparable[] aux = new Comparable[a.length];
        sortTD(a, aux, 0, a.length - 1);
    }
    
    // sort bottom up (non-recursive)
    public static void sortBU(Comparable[] a) {
        
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        
        for (int sz = 1; sz < N; sz = sz+sz)
            for ( int lo = 0; lo < N - sz; lo += sz+sz)
                merge(a, aux, lo, lo+sz-1, Math.min(lo + sz + sz -1, N-1));
    }
    
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;    
    }
    
    private static boolean less(Comparable v, Comparable w) { 
        return v.compareTo(w) < 0; 
    }
    
    public static String subarray(Comparable[] a) {
        
        StringBuilder temp = new StringBuilder();
        
        for ( int i = 0; i < a.length; i++ ){
            temp.append( " " + a[i]);
        }
        
        return temp.toString();
    }

}
