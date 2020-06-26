package algorithms.chapter1.resizingarray;

import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings({ "unchecked" })
public class LIFOStack<Item> implements Iterable<Item> {

    // Stack items
    private Item[] a = (Item[]) new Object[1];
    // Number of items
    private int N = 0;
    
    public boolean isEmpty()   { return N == 0; }
    public int size()          { return N; }
    
    public int arrayLength()          { return a.length; }
    
    
    // Move stack to a new array of size max.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }
    
    // Add item to top of stack
    public void addLast(Item item) {
        if (N == a.length) resize(2*a.length);
        a[N++] = item;
    }
    
    // Remove item from top of stack
    public Item removeLast() {
        Item item = a[--N];
        a[N] = null;
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        
        return new ReverseArrayIterator();
    }
    
    // support LIFO iteration
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = N;
        public boolean hasNext() { return i > 0;  }
        public Item next()       { return a[--i]; }
        public void remove()     {                }
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        /**
         * Test 1: addFirst, removeFirst, isEmpty, size
        */ 
        StdOut.println("Test 1:");

        LIFOStack<String> lifo = new LIFOStack<String>();
        
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        lifo.addLast(in.readString());
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        StdOut.println(lifo.removeLast() + " ");
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        
        lifo.addLast(in.readString());
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        lifo.addLast(in.readString());
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
//        StdOut.println("isEmpty(): " + lifo.isEmpty());
//        StdOut.println("size(): " + lifo.size());
//        
        StdOut.println(lifo.removeLast() + " ");
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        StdOut.println(lifo.removeLast() + " ");
        StdOut.println("isEmpty(): " + lifo.isEmpty());
        StdOut.println("size(): " + lifo.size());
        StdOut.println("length(): " + lifo.arrayLength());
        
        /**
         * Test 2: iterate
         */
        StdOut.println("Test 2:");
        Deque<String> lifo2 = new Deque<String>();
        
        while (!in.isEmpty()) {    
            String item = in.readString();
            lifo2.addFirst(item);
            StdOut.println("size(): " + lifo2.size());
            
            
        }
        
        for (String s: lifo2) {
            StdOut.println(s + " ");
        }    
        
        
        

    } 
}
