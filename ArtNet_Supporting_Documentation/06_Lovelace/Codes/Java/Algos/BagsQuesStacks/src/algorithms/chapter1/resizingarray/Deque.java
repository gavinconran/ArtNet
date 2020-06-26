package algorithms.chapter1.resizingarray;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
    
    // array a
    private Item[] a = (Item[]) new Object[1];
    // Number of items
    private int N = 0;
    // Tracks the front or the first of the deque
    private int first = 0; //500000;
    // Tracks the back or the last of the deque
    private int last = 0; //500000;
    
    // Is the deque empty?
    public boolean isEmpty()   { return N == 0; }
    // How large is the deque?
    public int size()          { return N; }
    
    // METHODS USED FOR TESTING --> Remove  when submitting to Coursera
    public int arrayLength()          { return a.length; }
    public int first()          { return first; }
    public int last()          { return last; }
    
    
    // Move deque to a new array of size max. extending deque to the right
    private void resizeLast(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i + first];
        
        // reset first and last
        first = 0;
        last = first + N;
        a = temp;
    }
    
    // Move deque to a new array of size max. extending deque to the left
    private void resizeFirst(int max) {
        Item[] temp = (Item[]) new Object[max];
        // if expanding
        if (max > a.length) {
            for (int j = 0; j < N; j++)
                temp[j+(max/2)] = a[j];
            first = max/2;
        } else { // else shrinking
            for (int j = 0; j < N; j++)
                temp[j] = a[j + first];
            first = 0;
        }
        
        last = first + N;
        a = temp;
        
    }
    
    // Add item to back of deque
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item value is null");
        } else {
            if (last == a.length) 
                resizeLast(2*a.length);
            
            a[last++] = item;
            N++; 
        }  
    }
        
    // Remove item from back of deque
    public Item removeLast() {
        if (isEmpty()) 
            throw new NoSuchElementException();
        
        Item item = a[--last];
        a[last] = null;
        N--;
        if (N > 0 && N == a.length/4) resizeLast(a.length/2);
        return item;
    }
    
    // Add item to top of deque
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item value is null");
        } else {
            if (first == 0) 
                resizeFirst(2*a.length);
            
            a[--first] = item;
            N++;
         }  
    }
    
    // Remove item from front of deque
    public Item removeFirst() {
        
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = a[first];
        a[first++] = null;
        N--;
        if (N > 0 && N == a.length/4) 
            resizeFirst(a.length/2);
        return item;
    }
    
    // Returns an iterator to the calling client
    @Override
    public Iterator<Item> iterator() {
        
        return new ArrayIterator();
    }
    
    // support LIFO iteration
    private class ArrayIterator implements Iterator<Item> { 
        
        private int counterN;
        
        private Object[] items;
        
        private ArrayIterator() {
            
            items = new Object[N];
            counterN = 0;
            
            for (int i = 0; i < N; i++) {
                items[i] = a[first+i];
            }
        }
        
        public boolean hasNext() {
            return   counterN < N;
        }
        
        public Item next() { 
            
            if (!hasNext()) 
                throw new NoSuchElementException();
            return (Item) items[counterN++];
        }
        
        public void remove() { 
            throw new UnsupportedOperationException("Invalid operation for Deque."); 
        }
    }
}
