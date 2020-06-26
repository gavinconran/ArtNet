package algorithms.chapter1.linkedlist;

import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BagList<Item> implements Iterable<Item> {
    
    private Node first;
    private int N;
    
    // Nested class to define nodes
    private class Node {
        Item item;
        Node next;
    }
    
    public boolean isEmpty() { return first == null; }  // Or: N == 0
    public int size()        { return N; }
    
    // Add item to top of stack
    public void add(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
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
        
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        BagList<String> bag = new BagList<String>();
        
        while (!in.isEmpty()) {    
            String item = in.readString();
            if (!item.equals("-"))
                bag.add(item);
        }        
        
        for (String s: bag)
            StdOut.println(s + " ");

    }

}
