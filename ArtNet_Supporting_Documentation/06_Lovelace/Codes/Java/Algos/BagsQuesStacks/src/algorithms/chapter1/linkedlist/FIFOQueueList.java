package algorithms.chapter1.linkedlist;

import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FIFOQueueList<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int N;
    
    // Nested class to define nodes
    private class Node {
        Item item;
        Node next;
    }
    
    public boolean isEmpty() { return first == null; }  // Or: N == 0
    public int size()        { return N; }
    
    // Add item to the end of the list
    public void enqueue(Item item) 
    {    
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldLast.next = last;
        N++;
    }
    
    // Remove item from top of stack
    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
    
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        
        private Node current = first;

        @Override
        public boolean hasNext() {
            
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() { }
        
    }
    
    public static void main(String[] args) {
        
        In in = new In(args[0]);
        FIFOQueueList<String> s = new FIFOQueueList<String>();
        
        while (!in.isEmpty()) {    
            String item = in.readString();
            if (!item.equals("-"))
                s.enqueue(item);
            else if (!s.isEmpty()) StdOut.print(s.dequeue() + " ");
        }        
        StdOut.println("(" + s.size() + " left on queue)");
    }


}
