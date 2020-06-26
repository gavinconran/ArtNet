package algorithms.chapter3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point2DTest {
    
    Point2D p1;
    Point2D p2;
    Point2D p3;
    Point2D p4;

    @Before
    public void setUp() throws Exception {
        
        p1 = new Point2D(0.5, 0.5);
        p2 = new Point2D(0.1, 0.1);
        p3 = new Point2D(0.5, 0.5);
        p4 = new Point2D(0.1, 0.5);
        
    }

    @After
    public void tearDown() throws Exception {
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
    }
    
    @Test
    public void testX() {
        assertTrue(p1.x() == 0.5);
        assertTrue(p2.x() == 0.1);
        assertTrue(p3.x() == 0.5);
    }
    
    @Test
    public void testY() {
        assertTrue(p1.y() == 0.5);
        assertTrue(p2.y() == 0.1);
        assertTrue(p3.y() == 0.5);
    }
    
    @Test
    public void testDistanceTo() {
        assertTrue(p1.distanceTo(p4) == 0.4);
    }
    
    @Test
    public void testDistanceSquaredTo() {
        assertTrue(p1.distanceSquaredTo(p4) == 0.16000000000000003);
    }
    
    @Test
    public void testCompareTo() {
        assertTrue(p1.compareTo(p1) == 0 );
        assertTrue(p1.compareTo(p2) == 1 );
        assertTrue(p2.compareTo(p1) == -1 );
    }

    @Test
    public void testEquals() {
        assertFalse(p1.equals(p2));
        assertTrue(p1.equals(p3));   
    }
    
    @Test
    public void testToString() {
       assertTrue(p1.toString().equals("(0.5, 0.5)"));
    }

}
