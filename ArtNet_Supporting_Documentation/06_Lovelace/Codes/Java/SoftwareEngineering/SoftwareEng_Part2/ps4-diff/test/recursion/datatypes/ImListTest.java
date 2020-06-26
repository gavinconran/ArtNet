package recursion.datatypes;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImListTest {
    
    @Test(expected=UnsupportedOperationException.class)
    public void testFirstOfEmpty() {
        ImList<Integer> nil = ImList.empty();
        nil.first();
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testRestOfEmpty() {
        ImList<Integer> nil = ImList.empty();
        nil.rest();
    }

    @Test
    public void testSize() {        
        ImList<Integer> nil = ImList.empty();  // nil = empty()
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        assertEquals(3, x.size());
    }
    
    @Test
    public void testContains() {        
        ImList<Integer> nil = ImList.empty();  // nil = empty()
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        assertTrue(x.contains(2));
        assertTrue(x.contains(0));
        assertTrue(x.contains(1));
    }
    
    @Test
    public void testGet() {        
        ImList<Integer> nil = ImList.empty();  // nil = empty()        
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        int result1 = x.get(0);
        assertEquals(0, result1);
        int result2 = x.get(1);
        assertEquals(1, result2);
        int result3 = x.get(2);
        assertEquals(2, result3);
    }
    
    @Test
    public void testIsEmpty() {
        ImList<Integer> nil = ImList.empty();  // nil = empty()
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        assertFalse(x.isEmpty());        
        ImList<Integer> y = ImList.empty();
        assertTrue(y.isEmpty());
    }
    
    @Test
    public void testCons() {
        ImList<Integer> nil = ImList.empty();  // nil = empty()
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        assertEquals(3, x.size());
        
        ImList<Integer> nilz = ImList.empty();
        ImList<Integer> z = nilz.cons(0); 
        assertEquals(1, z.size());
    }
    
    @Test
    public void testAppend() {
        ImList<Integer> nil = ImList.empty();  // nil = empty()
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        ImList<Integer> nilz = ImList.empty();
        ImList<Integer> z = nilz.cons(0); 
        assertEquals(3, x.size());
        assertEquals(1, z.size());
        assertEquals(4, z.append(x).size());
    }
    
    @Test
    public void testReverse() {
        ImList<Integer> nil = ImList.empty();  // nil = empty()        
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        int result1 = x.reverse().get(0);
        assertEquals(2, result1);
        int result2 = x.reverse().get(1);
        assertEquals(1, result2);
        int result3 = x.reverse().get(2);
        assertEquals(0, result3);
    }
}
