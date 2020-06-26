package algorithms.chapter2.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
import algorithms.chapter2.assignments.Point;
import algorithms.chapter2.assignments.BruteCollinearPoints;

public class bruteColinearPointsTest {
    
    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;
    
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
        
        nullPoint = null;
        
        Point[] points = new Point[5];
        points[0] = point1;
        points[1] = point2;
        points[2] = point3;
        points[3] = point4;
        points[4] = point5;
        
        Point[] pointsWithNullPoint = new Point[6];
        pointsWithNullPoint[0] = point1;
        pointsWithNullPoint[1] = point2;
        pointsWithNullPoint[2] = point3;
        pointsWithNullPoint[3] = point4;
        pointsWithNullPoint[4] = point5;
        pointsWithNullPoint[5] = nullPoint;
        
        Point[] pointsWithRepeatedPoint = new Point[6];
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
        
//        point1 = null;
//        point2 = null;
//        point3 = null;
//        point4 = null;
//        point5 = null;
//        
//        points = null;
//        pointsWithNullPoint = null;
//        pointsWithRepeatedPoint = null;
    }

    @Test(expected=NullPointerException.class)
    public void testBruteCollinearPointsConstructor() {
        
        StdOut.println("points.length: " + points.length);
        StdOut.println("nullPoints.length: " + nullPoints.length);
        StdOut.println("pointsWithNullPoint.length: " + pointsWithNullPoint.length);
        StdOut.println("pointsWithRepeatedPoint.length: " + pointsWithRepeatedPoint.length);

        
        new BruteCollinearPoints(nullPoints);
    }
    
    @Test(expected=NullPointerException.class)
    public void testBruteCollinearPointsConstructorWithNullPoint() {
        
        new BruteCollinearPoints(points);
    }
//    
//    @Test(expected=IllegalArgumentException.class)
//    public void testBruteCollinearPointsConstructorWithRepeatedPoint() {
//        
//        new BruteCollinearPoints(pointsWithRepeatedPoint);
//    }

}
