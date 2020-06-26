package recursion.datatypes;
// immutable lists: a classic, canonical example of an immutable datatype
// data-type definitions: a powerful way to think about abstract types, particularly recursive ones
// functions over recursive data-types: declared in the specification for the type, and implemented with one case per concrete variant
// INTERPRETER PATTERN
// pattern of implementing an operation over a recursive data type

public interface ImList<E> {
        
        /**
         * factory Method
         * @return  empty list
         */
        public static <E> ImList<E> empty() {
            return new Empty<>();
        }
        
        /**
         * @param e:element
         * @return new list formed by adding an element to the front of this list
         */
        public ImList<E> cons(E e);
        
        /**
         * @ return first element of a list, requires the list to be nonempty
         */
        public E first();
        
        /**
         * @ return list of all elements of this list except for the first, requires the list to be nonempty
         */
        public ImList<E> rest();
        
        /**
         * 
         * @ returns number of elements in the list
         */
        public int size();
        
        /**
         * @return true iff this list is empty
         */
        public boolean isEmpty();
        
        /**
         * @return true iff Element e is in this list
         */
        public boolean contains(E e);
        
        /**
         * @param list index i
         * @return Element e at position i in this list
         */
        public E get(int n);
        
        /**
         * @param other list of type InList<E>
         * @return a new list made up by appending this list and that list
         */
        public ImList<E> append(ImList<E> other);
        
        /**
         * @return a new list whic is the reverse of this list
         */
        public ImList<E> reverse();
}
