package algorithms.chapter2.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
import algorithms.chapter2.assignments.BruteCollinearPoints;
import algorithms.chapter2.assignments.FastCollinearPoints;
import algorithms.chapter2.assignments.LineSegment;
import algorithms.chapter2.assignments.Point;

public class pointTest {
    
    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;
    Point point6;
    
    Point p;
    Point q;
    Point r;
    
    Point p1;
    Point q1;
    Point r1;
    
    Point p2;
    Point q2;
    Point r2;
    
    Point p3;
    Point q3;
    
    LineSegment line1to2;
    LineSegment line2to5;
    LineSegment line3to4;
    
    Point nullPoint;
    
    Point[] points;
    Point[] nullPoints;
    Point[] pointsWithNullPoint;
    Point[] pointsWithRepeatedPoint;

    

    @Before
    public void setUp() throws Exception {
        
        point1 = new Point(1,1);
        point2 = new Point(2,2);   
        point3 = new Point(3,2);
        point4 = new Point(1,5);
        point5 = new Point(3,3);
        point6 = new Point(4,4);
        
        p = new Point(99, 211);
        q = new Point(156, 228);
        r = new Point(472, 157);
        
        p1 = new Point(1365, 27867);
        q1 = new Point(15478, 31064);
        r1 = new Point(7678, 30562);
        
        p2 = new Point(3, 5);
        q2 = new Point(9, 0);
        r2 = new Point(7, 5);
        
        p3 = new Point(55, 232);
        q3 = new Point(369, 232);
        
        line1to2 = new LineSegment(point1, point2);
        line2to5 = new LineSegment(point2, point5);
        line3to4 = new LineSegment(point3, point4);
        
        nullPoint = null;
        
        points = new Point[6];
        points[0] = point1;
        points[1] = point2;
        points[2] = point3;
        points[3] = point4;
        points[4] = point5;
        points[5] = point6;
        
        pointsWithNullPoint = new Point[6];
        pointsWithNullPoint[0] = point1;
        pointsWithNullPoint[1] = point2;
        pointsWithNullPoint[2] = point3;
        pointsWithNullPoint[3] = point4;
        pointsWithNullPoint[4] = point5;
        pointsWithNullPoint[5] = nullPoint;
        
        pointsWithRepeatedPoint = new Point[6];
        pointsWithRepeatedPoint[0] = point1;
        pointsWithRepeatedPoint[1] = point2;
        pointsWithRepeatedPoint[2] = point3;
        pointsWithRepeatedPoint[3] = point4;
        pointsWithRepeatedPoint[4] = point5;
        pointsWithRepeatedPoint[5] = point1;
        
        nullPoints = null;
    }

    @After
    public void tearDown() throws Exception {
        
        point1 = null;
        point2 = null;
        point3 = null;
        point4 = null;
        point5 = null;
        
        line1to2 = null;
        line2to5 = null;
        line3to4 = null;
        
        p = null;
        q = null;
        r = null;
        
        p1 = null;
        q1 = null;
        r1 = null;
        
        p2 = null;
        q2 = null;
        r2 = null;
        
        p3 = null;
        q3 = null;
        
        points = null;
        nullPoints = null;
        pointsWithNullPoint = null;
        pointsWithRepeatedPoint = null;
    }
    
    /**
     * Test 1: Draw points
    */ 
    @Test
    public void testDraw() {
        
        point1.draw();
        point2.draw();
    }
    
    /**
     * Test 2: Draw line between points
    */ 
    @Test
    public void testDrawTo() {
        
        point1.drawTo(point2);
    }
    
    /**
     * Test 3: Test if points are eual to each other
    */ 
    @Test
    public void testCompareTo() {
        // Test point1.y(1) < point2.y(2) AND point1.x(1) < point2.x(2) returns -1 ,i.e. point 1 < point2
        assertEquals(point1.compareTo(point2), -1);
        // Test point2.y(2) < point1.y(1) AND point2.x(2) < point1.x(1) returns +1 ,i.e. point 1 > point2
        assertEquals(point2.compareTo(point1), 1);
        // Test point2.y(2) == point3.y(2) AND point2.x(2) < point3.x(3) returns -1 ,i.e. point 3 < point2
        assertEquals(point2.compareTo(point3), -1);
        // Test point1.y(1) == point1.y(1) AND point1.x(1) < point1.x(1) returns 0 ,i.e. point 1 == point1
        assertEquals(point1.compareTo(point1), 0);
    }
    
    /**
     * Test 4: Test the slope between points
    */ 
    @Test
    public void testSlopeTo() {
        // Test point1 and point2 are equal return Double.NEGATIVE_INFINITY
        assertTrue(point1.slopeTo(point1) == Double.NEGATIVE_INFINITY);
        
        // Test point1.x and point4.x are equal return Double.POSITIVE_INFINITY
        assertTrue(point1.slopeTo(point4) == Double.POSITIVE_INFINITY);
        
        // Test point2.y and point3.y are equal return Double.NEGATIVE_INFINITY
        assertTrue(point2.slopeTo(point3) == +0.0);
        
        // Test point2.y and point3.y are equal return Double.NEGATIVE_INFINITY
        assertTrue(point2.slopeTo(point3) == +0.0);
        
        // Test slope of point1 and point2 returns 1
        assertTrue(point1.slopeTo(point2) == 1);
        
       // Test slope of point3 and point2 returns 1/2
        //StdOut.println(point2.slopeTo(point4));
        assertTrue(point2.slopeTo(point4) == -3.0);    
        
//        StdOut.println("p.slopeTo(q): " + p.slopeTo(q));
        assertTrue(p.slopeTo(q) == 0.2982456140350877);
        
//        StdOut.println("p.slopeTo(r): " + p.slopeTo(r));
        assertTrue(p.slopeTo(r) == -0.1447721179624665);
        
//        StdOut.println("p1.slopeTo(q1): " + p1.slopeTo(q1));
        assertTrue(p1.slopeTo(q1) == 0.22652873237440657);
        
//        StdOut.println("p1.slopeTo(r1): " + p1.slopeTo(r1));
        assertTrue(p1.slopeTo(r1) == 0.4268968794550927);
        
//        StdOut.println("p2.slopeTo(q2): " + p2.slopeTo(q2));
        assertTrue(p2.slopeTo(q2) == -0.8333333333333334);
        
//        StdOut.println("p2.slopeTo(r2): " + p2.slopeTo(r2));
        assertTrue(p2.slopeTo(r2) == +0.0);
        
//        StdOut.println("p3.slopeTo(q3): " + p3.slopeTo(q3));
        assertTrue(p3.slopeTo(q3) == +0.0);
    }
    
    /**
     * Test 5: test if points have the same slope and therefore in the same line segment
    */ 
    @Test
    public void testSlopeOrder() { 
        
        // test if 3 points on the same line have the same gradient
        assertTrue(point1.slopeOrder().compare(point2, point5) == 0);   
        assertTrue(point2.slopeOrder().compare(point1, point5) == 0); 
        assertTrue(point5.slopeOrder().compare(point2, point1) == 0); 
        
        // test if 3 points NOT on the same which gradient id higher p5 to p2 or p5 to p4
        assertTrue(point5.slopeOrder().compare(point2, point4) == 1);  
     
        // test if 3 points NOT on the same which gradient id higher p5 to p4 or p5 to p2
        assertTrue(point5.slopeOrder().compare(point4, point2) == -1); 
        
//        StdOut.println("p.slopeOrder().compare(q, r): " + p.slopeOrder().compare(q, r));
        assertTrue(p.slopeOrder().compare(q, r) == 1); 
        
        assertTrue(p1.slopeOrder().compare(q1, r1) == -1); 
        
//        StdOut.println("p2.slopeOrder().compare(q2, r2): " + p2.slopeOrder().compare(q2, r2));
        assertTrue(p2.slopeOrder().compare(q2, r2) == -1); 
    }
    
    /**
     * Test 6: Print line segments
    */ 
    @Test
    public void testLineSegment()
    {
//        StdOut.println(line1to2.toString());
//        StdOut.println(line2to5.toString());
//        StdOut.println(line3to4.toString());
        
    }
    
    /**
     * Test 7: Test creation of BruteCollinearPoints with nullPoints array
    */ 
    @Test(expected=NullPointerException.class)
    public void testBruteCollinearPointsConstructor() {
        
        new BruteCollinearPoints(nullPoints);
    }
    
    /**
     * Test 8: Test Good array Brute
    */ 
    @Test
    public void testBruteGoodArray() {
        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points); 
        
        assertTrue(collinear.numberOfSegments() == 1);
        
        LineSegment[] segments = collinear.segments();
        assertTrue(segments.length == 1);
        StdOut.println(segments[0].toString());
        
    }
    /**
     * Test 9: Test with null point in array Brute
    */ 
    @Test(expected=NullPointerException.class)
    public void testBruteCollinearNullPointsConstructor() {
        
        new BruteCollinearPoints(pointsWithNullPoint);
    }
    
    /**
     * Test 10: Test duplicates Brute
    */ 
    @Test(expected=IllegalArgumentException.class)
    public void testBruteDuplicates() {
        
        new BruteCollinearPoints(pointsWithRepeatedPoint); 
        
    }
    
    /**
     * Test 11: Test No duplicates or Null points: Fast
    */ 
    @Test
    public void testNoDuplicatesFast() {

        FastCollinearPoints collinear = new FastCollinearPoints(points); 
        
        StdOut.println("collinear.numberOfSegments(): " + collinear.numberOfSegments());
        assertTrue(collinear.numberOfSegments() == 1);
        
    }    
    
    /**
     * Test 12: Test creation of FastCollinearPoints with nullPoints array
    */ 
    @Test(expected=NullPointerException.class)
    public void testFastCollinearPointsConstructor() {
        
        new FastCollinearPoints(nullPoints);
    }
    
    /**
     * Test 13: Test creation of FastCollinearPoints with pointsWithNullPoint array
    */ 
    @Test(expected=NullPointerException.class)
    public void testFastCollinearNullPointsConstructor() {
        
        new FastCollinearPoints(pointsWithNullPoint);
    }
    /**
     * Test 14: Test creation of FastCollinearPoints with pointsWithRepeatedPoint array
    */ 
    @Test(expected=IllegalArgumentException.class)
    public void testfastDuplicates() {
        
        new FastCollinearPoints(pointsWithRepeatedPoint); 
        
    }
    
   
}
