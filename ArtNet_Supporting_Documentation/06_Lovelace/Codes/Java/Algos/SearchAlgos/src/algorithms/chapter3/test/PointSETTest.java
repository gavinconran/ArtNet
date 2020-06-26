package algorithms.chapter3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import algorithms.chapter3.kdtree.PointSET;

public class PointSETTest {
    
    Point2D p1;
    Point2D p2;
    Point2D p3;
    Point2D p4;
    
    PointSET pointSET;
    
    PointSET pointSETTest1;
    
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
        p2 = new Point2D(0.1, 0.1);
        p3 = new Point2D(0.5, 0.5);
        p4 = new Point2D(0.1, 0.5);
        
        pointSET = new PointSET();
        
        xmin = 0.0;
        xmax = 1.0;
        ymin = 0.0;
        ymax = 1.0;
        
        rect1 = new RectHV(xmin, ymin, xmax, ymax);
        rect2 = new RectHV(xmin -0.2, ymin, xmax, ymax);
        rect3 = new RectHV(0, 0.1, 0, 0.1);
        
        In inTest1 = new In("/home/gavin/Java/Algos/week5/assignment/kdtree/horizontal8.txt");
        pointSETTest1 = new PointSET();
        while (!inTest1.isEmpty()) {
            double x = inTest1.readDouble();
            double y = inTest1.readDouble();
            Point2D p = new Point2D(x, y);
            pointSETTest1.insert(p);
        }
    }

    @After
    public void tearDown() throws Exception {
        
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        
        pointSET = null;
    }

    @Test
    public void testIsEmpty() {
        assertTrue(pointSET.isEmpty());
    }
    
    @Test
    public void testSize() {
        assertTrue(pointSET.size() == 0);
    }
    
    @Test(expected=NullPointerException.class)
    public void testInsertException() {
        
        pointSET.insert(null);
    }
    
    @Test
    public void testInsert() {
        
        pointSET.insert(p1);
        assertTrue(pointSET.size() == 1);
        assertFalse(pointSET.isEmpty());
    }
    
    @Test(expected=NullPointerException.class)
    public void testContainsException() {
        
        pointSET.contains(null);
    }
    
    @Test
    public void testContains() {
        
        pointSET.insert(p1);
        assertTrue(pointSET.contains(p1));
        assertFalse(pointSET.contains(p2));
    }
    
    @Test
    public void testRange() {   
      
      Bag<Point2D> bag = new Bag<Point2D>();  
        
      Iterable<Point2D> point2DIter = pointSETTest1.range(rect1);
      for (Point2D p: point2DIter) {
//          StdOut.println(p);   
          bag.add(p);
      }
      
      assertTrue(bag.size() == 8);       
    }
    
    @Test
    public void testNearest() {   
      StdOut.println(pointSETTest1.nearest(p2));
      assertFalse(pointSETTest1.nearest(p2) == p3);
           
    }

}
