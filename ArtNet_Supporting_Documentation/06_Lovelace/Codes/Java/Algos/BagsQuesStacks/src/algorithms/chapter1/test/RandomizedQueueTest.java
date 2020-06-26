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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
//import algorithms.chapter1.linkedlist.RandomizedQueue;
import algorithms.chapter1.resizingarray.RandomizedQueue;

public class RandomizedQueueTest {
    
    RandomizedQueue<String> queueOfStrings;
    RandomizedQueue<Integer> queueOfIntegers;
    
    List<Integer> testIntList;
    
    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
//        System.out.println("@BeforeClass - oneTimeSetUp");
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
//        System.out.println("@AfterClass - oneTimeTearDown");
    }
    
    @Before
    public void setUp() {
        // executed before each test
        queueOfStrings = new RandomizedQueue<String>();
        queueOfIntegers = new RandomizedQueue<Integer>();
        assertNotNull(queueOfStrings);
        assertNotNull(queueOfIntegers);
        
        testIntList = new ArrayList<Integer>();
        
//        System.out.println("@Before - setUp");
    }
    
    @After
    public void tearDown() {
        // executed before each test
        queueOfStrings = null;
        queueOfIntegers = null;
        
        testIntList.clear();
        
//        System.out.println("@After - tearDown");
    }
    
    /**
     * Test 1: empty String and Integer RandomizedQueues
     */
    @Test
    public void testQueue() throws Exception { 

        assertTrue(queueOfStrings.size() == 0);
        assertTrue(queueOfIntegers.size() == 0);

//        System.out.println("@Test - testEmptyQueue");
    }
    
    
    /**
     * Test 2: enqueue, dequeue, isEmpty, size for type String
     */
    @Test
    public void testOneItemQueue() throws Exception { 
        // String test
        String dequeueStringItem = "A";
        queueOfStrings.enqueue(dequeueStringItem);
        assertEquals(queueOfStrings.size(), 1);
        
        String enqueueStringItem = queueOfStrings.dequeue();
        assertTrue(dequeueStringItem == enqueueStringItem);
        
        // Integer test
        int enqueueIntegerItem = 0;
        queueOfIntegers.enqueue(enqueueIntegerItem);
        assertEquals(queueOfIntegers.size(), 1);
        StdOut.println("testOneItemQueue");
        for (Integer i: queueOfIntegers)
            StdOut.println("i:" + i);
        
        int dequeueIntegerItem = queueOfIntegers.dequeue();
        assertTrue(dequeueIntegerItem == enqueueIntegerItem);
        
//        System.out.println("@Test - testOneItemQueue");
    }
    
    /**
     * Test 3: enqueue, dequeue, isEmpty, size for type Integer
     */
    @Test
    public void enqueueQueueDequeue() throws Exception { 
        
        queueOfIntegers.enqueue(426);
        testIntList.add(426);
        queueOfIntegers.enqueue(366);
        testIntList.add(366);
        queueOfIntegers.enqueue(863);
        testIntList.add(863);

        assertEquals(queueOfIntegers.size(), 3);
        assertTrue(testIntList.contains(queueOfIntegers.dequeue()));
        assertTrue(testIntList.contains(queueOfIntegers.dequeue()));
        assertTrue(testIntList.contains(queueOfIntegers.dequeue()));
        assertEquals(queueOfIntegers.size(), 0);
        
        assertTrue(!testIntList.contains(0));
        
    }
    
    /**
     * Test 4: enqueue, sample, isEmpty, size for type Integer
     */
    @Test
    public void enqueueQueueSample() throws Exception { 
        
        queueOfIntegers.enqueue(426);
        testIntList.add(426);
        queueOfIntegers.enqueue(366);
        testIntList.add(366);
        queueOfIntegers.enqueue(863);
        testIntList.add(863);

        assertEquals(queueOfIntegers.size(), 3);
        assertTrue(testIntList.contains(queueOfIntegers.sample()));
        assertTrue(testIntList.contains(queueOfIntegers.sample()));
        assertTrue(testIntList.contains(queueOfIntegers.sample()));
        assertEquals(queueOfIntegers.size(), 3);
        
        assertTrue(!testIntList.contains(0));
        
    }
    
    /**
     * Test 5: dequeue() Exception test 
     */
    @Test(expected=NoSuchElementException.class)
    public void testdequeueException() throws Exception { 
        
        queueOfIntegers.dequeue();
    
    }   
    
    /**
     * Test 6: sample() Exception test 
     */
    @Test(expected=NoSuchElementException.class)
    public void testsampleException() throws Exception { 
        
        queueOfIntegers.sample();
    
    }   
    
    /**
     * Test 7: enqueue() Exception test 
     */
    @Test(expected=NullPointerException.class)
    public void testenqueueException() throws Exception { 
        
        String item = null;
        queueOfStrings.enqueue(item);
    
    }   
    
    /**
     * Test 8: Empty Iterator test for type Integer. Can test multiple exceptions by using try/catch
     */
    @Test
    public void testEmptyIterator() {
        Iterator<Integer> iterator = queueOfIntegers.iterator();
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
     * Test 9: Single element (followed by empty) Iterator test for type Integer. 
     */
    @Test
    public void testSingleElementIterator() {
        
        queueOfIntegers.enqueue(426);
        
        Iterator<Integer> iterator = queueOfIntegers.iterator();
        assertFalse(!iterator.hasNext());
        
        assertTrue(iterator.next() == 426);  
        
        queueOfIntegers.dequeue();
        Iterator<Integer> iterator2 = queueOfIntegers.iterator();
        assertFalse(iterator2.hasNext());
    }
    
    /**
     * Test 10: Nested element (followed by empty) Iterator test for type Integer. 
     */
    @Test
    public void testNestedElementIterator() {
        StdOut.println("Test10 ");
        queueOfIntegers.enqueue(426);
        queueOfIntegers.enqueue(427);
        queueOfIntegers.enqueue(428);
        queueOfIntegers.enqueue(429);
        queueOfIntegers.enqueue(450);
        for (Integer item: queueOfIntegers)
            StdOut.println("item: " + item);
            for (Integer item2: queueOfIntegers)
                StdOut.println("item2: " + item2);
        
    }
    

}
