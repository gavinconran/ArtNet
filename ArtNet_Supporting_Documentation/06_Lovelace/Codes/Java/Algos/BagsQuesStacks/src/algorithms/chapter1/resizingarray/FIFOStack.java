package algorithms.chapter1.resizingarray;

import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FIFOStack <Item> implements Iterable<Item> {
    
    // Stack items
    private Item[] b = (Item[]) new Object[1];
    // Number of items
    private int M = 0;
    private int first = 0;
    
    public boolean isEmpty()   { return M == 0; }
    public int size()          { return M; }
    
    public int arrayLength()          { return b.length; }
    
    // Move stack to a new array of size max.
    private void resizeB(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int j = 0; j < M; j++)
            temp[j+M] = b[j];
        b = temp;
    }
    
    // Add item to top of stack
    public void addFirst(Item item) {
        if (M == b.length) resizeB(2*b.length);
        first = b.length - M;
        b[--first] = item;
        M++;
    }
    
    public Item removeFirst() {
        Item item = b[first];
        b[first++] = null;
        M--;
        if (M > 0 && M == b.length/4) resizeB(b.length/2);
        return item;
    }
    
    @Override
    public Iterator<Item> iterator() {
        
        return new ArrayIterator();
    }
    
    // support LIFO iteration
    private class ArrayIterator implements Iterator<Item> {
        private int i = first + M;
        public boolean hasNext() { return i > first; } //first + M;  }
        public Item next()       { return b[--i]; }
        public void remove()     {                }
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
        /**
         * Test 1: addFirst, removeFirst, isEmpty, size
        */ 
//        StdOut.println("Test 1:");
//
//        FIFOStack<String> fifo = new FIFOStack<String>();
//        
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
//        fifo.addFirst(in.readString());
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
//        StdOut.println(fifo.removeFirst() + " ");
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
//        
//        fifo.addFirst(in.readString());
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
//        fifo.addFirst(in.readString());
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
////        StdOut.println("isEmpty(): " + fifo.isEmpty());
////        StdOut.println("size(): " + fifo.size());
////        
//        StdOut.println(fifo.removeFirst() + " ");
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
//        StdOut.println(fifo.removeFirst() + " ");
//        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        StdOut.println("size(): " + fifo.size());
//        StdOut.println("length(): " + fifo.arrayLength());
//        
////        
////        StdOut.println("isEmpty(): " + fifo.isEmpty());
//        
        /**
         * Test 2: iterate
         */
        
        In in = new In(args[0]);
        StdOut.println("Test 2:");
        FIFOStack<String> fifo2 = new FIFOStack<String>();
        
        while (!in.isEmpty()) {    
            String item = in.readString();
            fifo2.addFirst(item);
            StdOut.println("size(): " + fifo2.size());
            
            
        }
        
        for (String s: fifo2) {
            StdOut.println(s + " ");
        }    

    }

}
