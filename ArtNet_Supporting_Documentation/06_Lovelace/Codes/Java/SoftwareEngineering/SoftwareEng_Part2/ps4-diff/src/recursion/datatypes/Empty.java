package recursion.datatypes;
// SENTINEL OBJECTS PATTERN
// to signal the base case or endpoint of a data structure is an example of a design pattern called sentinel objects. 

public class Empty<E> implements ImList<E> {
    
    public Empty() {
        
    }

    @Override
    public ImList<E> cons(E e) {
        return new Cons<>(e, this);
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImList<E> rest() {
        throw new UnsupportedOperationException();
    }
    
    public int size() { return 0; }
    
    public boolean isEmpty() { return true; }
    
    public boolean contains(E e) { return false; }
    
    public E get(int n) { 
        throw new UnsupportedOperationException();
    }
    
    public ImList<E> append(ImList<E> other) { return other; }
    
    public ImList<E> reverse() { return new Empty<E>(); }
    
}
