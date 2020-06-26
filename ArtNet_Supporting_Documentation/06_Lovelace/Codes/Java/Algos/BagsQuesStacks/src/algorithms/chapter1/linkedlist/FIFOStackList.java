package algorithms.chapter1.linkedlist;

import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FIFOStackList<Item> implements Iterable<Item> {
    
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
    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }
    
    // Remove item from top of stack
    public Item pop() {
        Item item = first.item;
        first = first.next;
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
        FIFOStackList<String> s = new FIFOStackList<String>();
        
        while (!in.isEmpty()) {    
            String item = in.readString();
            if (!item.equals("-"))
                s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        
        StdOut.println("(" + s.size() + " left on stack)");
    }
}
