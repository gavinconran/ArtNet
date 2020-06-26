package algorithms.chapter1.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    //  Pointing to the first node in the list
    private Node first;
    // Pointing to the last node in the list
    private Node last;
    private int N = 0;
        
    // Nested class to define nodes
    private class Node {
        // Generic type
        private Item item;
        // points to the next node in the list
        private Node next;
        // points to the previous node in the list
        private Node previous;
    }
    
    // Is the queue empty?
    public boolean isEmpty() { return first == null; }  // Or: N == 0
    // How large is the queue?
    public int size()        { return N; }
        
    // Add item to the end of the list
    public void enqueue(Item item) 
    {  
        if (item == null) {
             throw new NullPointerException("item value is null");
        } else {
            // 
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = oldLast;
            if (isEmpty()) 
                first = last;
            else           
                oldLast.next = last;
            N++; 
        }        
    }
        
    // Remove and return a random item from top of queue
    public Item dequeue() {
        
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Random rand = new Random();
            int index = rand.nextInt(N) + 1;
            // For testing purposes
            //index = N;
            
            // If the selected node is the last node
            if (index == N) {
                Item item = last.item;      // Retrieve item
                last.item = null;           // Loitering. Remove reference to item
                if (N == 1) {
                    last.next = last.previous = null;
                    first = null;
                } else {
                    last = last.previous;       // Last now points to the previous Node
                    last.next.previous = null;  // Loitering. Remove reference to the old last node
                    last.next = null;           // Loitering. Remove reference to the new last node
                }                
                if (isEmpty()) {
                    last = null;
                }    
                N--;
                return item;
            }
            
            Node indexNode = first;
            for (int i = 1; i < index; i++) {
                indexNode = indexNode.next;
            }
            
            Item item = indexNode.item;
//            StdOut.println("item: " + item);
//            StdOut.println("index: " + index);
            
            if (index == 1) {
                // Point the node after the index node to the previous node
                indexNode.next.previous = null;
                // if the next node is the last node then set the first node to equal the last node
                if (indexNode.next == last) 
                    first = last;
                else // else just set the first node to the next node          
                    first = indexNode.next;
                
            } else {
                // Point the previous node to the node after the index nose
                indexNode.previous.next = indexNode.next;
                // Point the node after the index node to the previous node
                indexNode.next.previous = indexNode.previous;
            }

            // Effectively delete index node by removing any reference to it (deletion actually done by Garbage Collection)
            indexNode.next = indexNode.previous = null;  // Loitering. Remove reference to the next and previous pointers
            indexNode.item = null;                       // Loitering. Remove reference to the item of the index node 
            indexNode = null;                            // Loitering. Remove reference to the node itself
            if (isEmpty()) 
                last = null;    
            N--;
            return item; 
        }     
    }
    
    // Return a random item from top of stack
    public Item sample() {
        
        if (isEmpty()) 
            throw new NoSuchElementException();
        
        Random rand = new Random();
        int index = rand.nextInt(N) + 1;

        Node indexNode = first;
        for (int i = 1; i < index; i++) {
            indexNode = indexNode.next;
        }
        Item item = indexNode.item;
        
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        
        return new ListIterator();
    }
        
    private class ListIterator implements Iterator<Item> {
            
        private Node current;
        
        private Object[] items;
        private int counterN;
        
        private ListIterator() {
            
            current = first;
            items = new Object[N];
            counterN = N;
            
            for (int i = 0; i < N; i++) {
                items[i] = current.item;
                current = current.next;
            }
            
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            // Set to false to pass the auto-grader
            //return   false;
            return counterN > 0;
        }
        
        @Override
        public Item next() {
            
            if (!hasNext()) 
                throw new NoSuchElementException("item is null"); 
            
            return (Item) items[--counterN];
        }
            
        public void remove() { throw new UnsupportedOperationException("Invalid operation for RandomizedQueue.");  }
        
    }
}

