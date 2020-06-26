package algorithms.chapter3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class RectHVTest {
    
    Point2D p1;
    Point2D p2;
    Point2D p3;
    Point2D p4;
    
    Double xmin;
    Double xmax;
    Double ymin;
    Double ymax;
    
    RectHV rect1;
    RectHV rect2;
    RectHV rect3;

    @Before
    public void setUp() throws Exception {
        
        p1 = new Point2D(0.5, 0.5);
        p2 = new Point2D(0.8, 0.8);
        p3 = new Point2D(0.6, 0.6);
        p4 = new Point2D(0.1, 0.5);
        
        
        xmin = 0.6;
        xmax = 0.9;
        ymin = 0.6;
        ymax = 0.9;
        
        rect1 = new RectHV(xmin, ymin, xmax, ymax);
        rect2 = new RectHV(xmin -0.2, ymin, xmax, ymax);
        rect3 = new RectHV(0, 0.1, 0, 0.1);
        
    }

    @After
    public void tearDown() throws Exception {
        
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        
        xmin = null;
        xmax = null;
        ymin = null;
        ymax = null;
        
        rect1 = null;
        rect2 = null;
    }

    @Test
    public void testXmin() {
        assertTrue(rect1.xmin() == 0.6);
    }
    
    @Test
    public void testXmax() {
        assertTrue(rect1.xmax() == 0.9);
    }
    
    @Test
    public void testYmin() {
        assertTrue(rect1.ymin() == 0.6);
    }
    
    @Test
    public void testYmax() {
        assertTrue(rect1.ymax() == 0.9);
    }
    
    @Test
    public void testContains() {
        assertFalse(rect1.contains(p1));
        assertTrue(rect1.contains(p2));
        assertTrue(rect1.contains(p3));
    }
    
    @Test
    public void testDistanceTo() {
        assertTrue(rect1.distanceTo(p1) == 0.14142135623730948);
    }
    
    @Test
    public void testDistanceSquaredTo() {
        assertTrue(rect1.distanceSquaredTo(p1) == 0.01999999999999999);
        assertTrue(rect1.distanceSquaredTo(p2) == 0.0);
        assertTrue(rect1.distanceSquaredTo(p3) == 0.0);
        assertTrue(rect1.distanceSquaredTo(p4) == 0.26);
    }
    
    @Test
    public void testIntersects() {
        assertTrue(rect1.intersects(rect2));
        assertFalse(rect1.intersects(rect3));
    }
    
    @Test
    public void testEquals() {
        assertFalse(rect1.equals(rect2));
        assertTrue(rect1.equals(rect1));   
    }
    
    @Test
    public void testToString() {
       assertTrue(rect1.toString().equals("[0.6, 0.9] x [0.6, 0.9]"));
    }

}
