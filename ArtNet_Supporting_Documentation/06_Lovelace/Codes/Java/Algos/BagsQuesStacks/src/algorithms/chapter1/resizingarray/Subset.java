package algorithms.chapter1.resizingarray;

import algorithms.chapter1.linkedlist.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    
    private static void exch(Comparable[] a, int i, int j) {
        
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    
    private static void shuffle(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(i + 1);
            exch(a, i, r);
        }
    }

    public static void main(String[] args) {
        
        // Using StdOut
        int K = Integer.parseInt(args[0]);
        
        // using In: Not used for submission
//        In in = new In(args[0]);      // 
//        int K = in.readInt();         // input from command line 
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
//        while (!in.isEmpty()) {
        while (!StdIn.isEmpty()) {    
//          String item = in.readString();
          String item = StdIn.readString();
          queue.enqueue(item);          
        } 
        
        for (int k = 0; k < K; k++)
            StdOut.println(queue.dequeue());

    }

}
