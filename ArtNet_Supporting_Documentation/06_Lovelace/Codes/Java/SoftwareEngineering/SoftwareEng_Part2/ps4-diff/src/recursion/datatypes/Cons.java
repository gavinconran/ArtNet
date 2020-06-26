package recursion.datatypes;

public class Cons<E> implements ImList<E> {
    private final E e;
    private final ImList<E> rest;
    private int size = 0;
    // rep invariant:
    //   e != null, rest != null, size >= 0
    //   size > 0 implies size == 1+rest.size()

    public Cons(E e, ImList<E> rest) {
        this.e = e;
        this.rest = rest;
        
    }
    public ImList<E> cons(E e) {
        return new Cons<>(e, this);
    }
    public E first() {
        return e;
    }
    public ImList<E> rest() {
        return rest;
    }
    
    public int size() { 
        if (size == 0) size = 1 + rest.size();
        return size;
    }
    
    public boolean isEmpty() { return false; }
    
    public boolean contains(E e) { return this.e.equals(e) || rest.contains(e); }
    
    public E get(int n) { 
        if (n == 0)
            return first();
        else
            return rest.get(n - 1);
    }
    
    public ImList<E> append(ImList<E> other) { return new Cons<>(e, rest.append(other)); }
    
//    public ImList<E> reverse() { return new Cons<>(e, new Empty<E>()).append(rest.reverse()); }
    public ImList<E> reverse() { return rest.reverse().append(new Cons<>(e, new Empty<E>())); }
    
}