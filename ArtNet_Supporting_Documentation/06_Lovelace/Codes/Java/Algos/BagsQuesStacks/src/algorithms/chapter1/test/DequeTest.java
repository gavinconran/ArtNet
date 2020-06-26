package algorithms.chapter1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
import algorithms.chapter1.resizingarray.Deque;

public class DequeTest {
    
    Deque<String> dequeOfStrings;
    Deque<Integer> dequeOfIntegers;
    
    List<Integer> testIntList;
    
    @Before
    public void setUp() {
        // executed before each test
        dequeOfStrings = new Deque<String>();
        dequeOfIntegers = new Deque<Integer>();
        assertNotNull(dequeOfStrings);
        assertNotNull(dequeOfIntegers);
        
        testIntList = new ArrayList<Integer>();
        
    }
    
    @After
    public void tearDown() {
        // executed before each test
        dequeOfStrings = null;
        dequeOfIntegers = null;
        
        testIntList.clear();
        
    }
    
    /**
     * Test 1: empty String and Integer Deques
    */ 
    @Test
    public void testDeque() throws Exception { 

        assertTrue(dequeOfStrings.size() == 0);
        assertTrue(dequeOfIntegers.size() == 0);

    }
    
    /**
     * Test 2: addFirst, removeFirst, isEmpty, size
    */ 
    @Test
    public void testOneItemDequeFirst() throws Exception { 
        // String test
        String dequeAddStringItem = "A";
        dequeOfStrings.addFirst(dequeAddStringItem);
        assertEquals(dequeOfStrings.size(), 1);
        assertTrue(!dequeOfStrings.isEmpty());
        
        String dequeRemoveStringItem = dequeOfStrings.removeFirst();
        assertTrue(dequeAddStringItem == dequeRemoveStringItem);
        assertEquals(dequeOfStrings.size(), 0);
        assertTrue(dequeOfStrings.isEmpty());
        
        // Integer test
        int dequeAddIntegerItem = 0;
        dequeOfIntegers.addFirst(dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 1);
        assertTrue(!dequeOfIntegers.isEmpty());
        
        int dequeueIntegerItem = dequeOfIntegers.removeFirst();
        assertTrue(dequeueIntegerItem == dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
        
    }
    
    /**
     * Test 3: addLast, removeLast, isEmpty, size
     */
    @Test
    public void testOneItemDequeLast() throws Exception { 
        // String test
        String dequeAddStringItem = "A";
        dequeOfStrings.addLast(dequeAddStringItem);
        assertEquals(dequeOfStrings.size(), 1);
        assertTrue(!dequeOfStrings.isEmpty());
        
        String dequeRemoveStringItem = dequeOfStrings.removeLast();
        assertTrue(dequeAddStringItem == dequeRemoveStringItem);
        assertEquals(dequeOfStrings.size(), 0);
        assertTrue(dequeOfStrings.isEmpty());
        
        // Integer test
        int dequeAddIntegerItem = 0;
        dequeOfIntegers.addLast(dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 1);
        assertTrue(!dequeOfIntegers.isEmpty());
        
        int dequeueIntegerItem = dequeOfIntegers.removeLast();
        assertTrue(dequeueIntegerItem == dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
        
    }
    
    /**
     * Test 4: addFirst, removeLast, isEmpty, size
     */
    @Test
    public void testOneItemDequeAddFirstRemoveLast() throws Exception { 
    
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
        
        int dequeAddIntegerItem = 111;
        dequeOfIntegers.addFirst(dequeAddIntegerItem);
        
        assertEquals(dequeOfIntegers.size(), 1);
        assertTrue(!dequeOfIntegers.isEmpty());
        
        int dequeueIntegerItem = dequeOfIntegers.removeLast();
        assertTrue(dequeueIntegerItem == dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
    }
    
    
    
    /**
     * Test 5: addLast, removeFirst, isEmpty, size
     */
    @Test
    public void testOneItemDequeAddLastRemoveFirst() throws Exception { 
    
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
        
        int dequeAddIntegerItem = 111;
        dequeOfIntegers.addLast(dequeAddIntegerItem);
        
        assertEquals(dequeOfIntegers.size(), 1);
        assertTrue(!dequeOfIntegers.isEmpty());
        
        int dequeueIntegerItem = dequeOfIntegers.removeFirst();
        assertTrue(dequeueIntegerItem == dequeAddIntegerItem);
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
    }    
    
    
    /**
     * Test 6: addFirst, addLast, isEmpty, size
     */
    @Test
    public void testOneItemDequeAddFirstAddLast() throws Exception { 
        
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());
        
        int dequeAddIntegerItem1 = 111;
        int dequeAddIntegerItem2 = 222;
        dequeOfIntegers.addFirst(dequeAddIntegerItem1);
        dequeOfIntegers.addLast(dequeAddIntegerItem2);
        assertEquals(dequeOfIntegers.size(), 2);
        assertTrue(!dequeOfIntegers.isEmpty());
        
        assertTrue(dequeOfIntegers.removeFirst() == dequeAddIntegerItem1);
        assertTrue(dequeOfIntegers.removeFirst() == dequeAddIntegerItem2);
        assertEquals(dequeOfIntegers.size(), 0);
        assertTrue(dequeOfIntegers.isEmpty());

    }
    
    /**
     * Test 7: iterate (adding using addFirst and addLast to fill deque 
     */
    @Test
    public void testIterator() throws Exception { 
        
//        dequeOfIntegers.addFirst(111);
//        testIntList.add(111);
        dequeOfIntegers.addLast(222);
        testIntList.add(222);
//        dequeOfIntegers.addFirst(333);
//        testIntList.add(333);
        dequeOfIntegers.addLast(444);
        testIntList.add(444);
//        dequeOfIntegers.addFirst(555);
//        testIntList.add(555);
        dequeOfIntegers.addLast(666);
        testIntList.add(666);
        
//        assertEquals(dequeOfIntegers.removeFirst(), 555, 0);
        
        for (int i: dequeOfIntegers) {
            assertTrue(testIntList.contains(i));
            for (int j: dequeOfIntegers){
                assertTrue(testIntList.contains(j));
                StdOut.println("j: " + j);
            }                
        }
    }
    
    /**
     * Test 8: removeFirst() Exception test 
     */
    @Test(expected=NoSuchElementException.class)
    public void testremoveFirstException() throws Exception { 
        
        dequeOfStrings.removeFirst();
    
    }   
    
    /**
     * Test 9: removeLast() Exception test 
     */
    @Test(expected=NoSuchElementException.class)
    public void testremoveLastException() throws Exception { 
        
        dequeOfStrings.removeLast();
    
    }   
    
    
    /**
     * Test 10: addFirst() Exception test 
     */
    @Test(expected=NullPointerException.class)
    public void testaddFirstException() throws Exception { 
        
        String item = null;
        dequeOfStrings.addFirst(item);
    
    }   
    
    /**
     * Test 11: addLast() Exception test 
     */
    @Test(expected=NullPointerException.class)
    public void testaddLastException() throws Exception { 
        
        String item = null;
        dequeOfStrings.addLast(item);
    
    }   
    
    /**
     * Test 12: Empty Iterator test for type Integer. Can test multiple exceptions by using try/catch
     */
    @Test
    public void testEmptyIterator() {
        Iterator<Integer> iterator = dequeOfIntegers.iterator();
        assertFalse(iterator.hasNext());
        try {
          iterator.next();
          fail("no exception thrown");
        } catch (NoSuchElementException expected) {
            
        }
        try {
          iterator.remove();
          fail("no exception thrown");
        } catch (UnsupportedOperationException expected) {
            
        }
    }
    
    /**
     * Test 13: Single (followed by empty) element Iterator test for type Integer. 
     */
    @Test
    public void testSingleElementIterator() {
        
        dequeOfIntegers.addLast(426);  // can be addLast or addFirst
        
        Iterator<Integer> iterator = dequeOfIntegers.iterator();
        assertFalse(!iterator.hasNext());
        
        assertTrue(iterator.next() == 426);  
        
        dequeOfIntegers.removeLast();  // can be removeLast or removeLast
        Iterator<Integer> iterator2 = dequeOfIntegers.iterator();
        assertFalse(iterator2.hasNext());
    }
    
    @Test
    public void testNestedElementIterator() {
        dequeOfIntegers.addLast(426);
        dequeOfIntegers.addLast(427);
        dequeOfIntegers.addLast(428);
        dequeOfIntegers.addLast(429);
        dequeOfIntegers.addLast(430);
        for (Integer item: dequeOfIntegers)
            StdOut.println("item: " + item);
            for (Integer item2: dequeOfIntegers)
                StdOut.println("item2: " + item2);
        
    }
    
    @Test
    public void testResizeLast() {
        
        // Add
        dequeOfIntegers.addLast(426);
        assertTrue(dequeOfIntegers.arrayLength() == 1);
        dequeOfIntegers.addLast(427);
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.addLast(428);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addLast(429);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addLast(430);
        assertTrue(dequeOfIntegers.arrayLength() == 8);        
        dequeOfIntegers.addLast(1426);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1427);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1428);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(1430);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2426);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2427);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2428);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2430);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        // Remove
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        StdOut.println("length: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("size: " + dequeOfIntegers.size());
        
        
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        
        
    }
    
    @Test
    public void testResizeFirst() {
        
        // Add
        assertTrue(dequeOfIntegers.arrayLength() == 1);
        dequeOfIntegers.addFirst(426);
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.addFirst(427);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addFirst(428);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addFirst(429);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(430);
        
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1426);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1427);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1428);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addFirst(1429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        
        // Remove
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
           
    }
    
    @Test
    public void testResizeLastFirst() {
        
        // Add
        dequeOfIntegers.addLast(426);
        assertTrue(dequeOfIntegers.arrayLength() == 1);
        dequeOfIntegers.addLast(427);
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.addLast(428);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addLast(429);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addLast(430);
        assertTrue(dequeOfIntegers.arrayLength() == 8);        
        dequeOfIntegers.addLast(1426);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1427);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1428);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addLast(1429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(1430);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2426);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2427);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2428);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addLast(2430);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
     // Remove
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.removeFirst();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        
        
    }
    
    @Test
    public void testResizeFirstLast() {
        
        // Add
        assertTrue(dequeOfIntegers.arrayLength() == 1);
        dequeOfIntegers.addFirst(426);
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        dequeOfIntegers.addFirst(427);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addFirst(428);
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        dequeOfIntegers.addFirst(429);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(430);
        
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1426);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1427);
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        dequeOfIntegers.addFirst(1428);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.addFirst(1429);
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        
        
        // Remove
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 16);
        for (Integer i: dequeOfIntegers)
            StdOut.println("i: " + i);
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        StdOut.println("length: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("sizeA: " + dequeOfIntegers.size());


        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 8);
        StdOut.println("length: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("sizeAA: " + dequeOfIntegers.size());
        
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 4);
        StdOut.println("length: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("sizeAAA: " + dequeOfIntegers.size());
        
        dequeOfIntegers.removeLast();
        assertTrue(dequeOfIntegers.arrayLength() == 2);
        
           
    }
    
    @Test
    public void testResults1() {
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        
        dequeOfIntegers.addFirst(0);
        StdOut.println("addFirst(0)");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("size: " + dequeOfIntegers.size());
        
        assertTrue(dequeOfIntegers.removeFirst() == 0);
        StdOut.println("removeFirst()");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("size: " + dequeOfIntegers.size());
        assertTrue(dequeOfIntegers.isEmpty());
        
        dequeOfIntegers.addFirst(6);
        StdOut.println("addFirst(6)");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        StdOut.println("size: " + dequeOfIntegers.size());

        dequeOfIntegers.addFirst(7);
        StdOut.println("addFirst(7)");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        
        for (Integer i: dequeOfIntegers)
            StdOut.println("i: " + i);
        
        assertTrue(dequeOfIntegers.removeFirst() == 7);
        StdOut.println("removeFirst(7)");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        for (Integer j: dequeOfIntegers)
            StdOut.println("j: " + j);
        dequeOfIntegers.addFirst(9);
        assertTrue(dequeOfIntegers.removeFirst() == 9);
        assertTrue(dequeOfIntegers.removeFirst() == 6);
    }
    
    @Test
    public void testResults2() {
        
        dequeOfIntegers.addLast(0);
        assertTrue(dequeOfIntegers.removeFirst() == 0);
        dequeOfIntegers.addLast(2);
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        dequeOfIntegers.addLast(3);
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        dequeOfIntegers.addLast(4);
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        dequeOfIntegers.addLast(5);
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        dequeOfIntegers.addLast(6);
        
        StdOut.println("testResults2");
        StdOut.println("arrayLength: " + dequeOfIntegers.arrayLength());
        StdOut.println("size: " + dequeOfIntegers.size());
        StdOut.println("first: " + dequeOfIntegers.first());
        StdOut.println("last: " + dequeOfIntegers.last());
        for (Integer j: dequeOfIntegers)
            StdOut.println("j: " + j);
        assertTrue(dequeOfIntegers.removeFirst() == 2);
        
    }
    
    @Test
    public void testResults3() {
        dequeOfIntegers.isEmpty();
        dequeOfIntegers.addFirst(1);
        assertTrue(dequeOfIntegers.removeLast() == 1);
        dequeOfIntegers.addLast(3);
        dequeOfIntegers.addFirst(4);
        dequeOfIntegers.addLast(5);
        assertTrue(dequeOfIntegers.removeFirst() == 4);
        assertTrue(dequeOfIntegers.removeLast() == 5);
        assertTrue(dequeOfIntegers.removeFirst() == 3);
        
    }
    
    
    
        
}
