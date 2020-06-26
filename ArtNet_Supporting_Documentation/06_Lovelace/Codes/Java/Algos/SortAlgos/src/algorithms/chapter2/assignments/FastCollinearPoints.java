package algorithms.chapter2.assignments;

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private Bag<LineSegment> bagOfSegments;
    private Point[] aux;
    
    private ST<Point, LineSegment> st1;
    private ST<Point, LineSegment> st2;

    
    // Constructor
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) {
            // check foe null input
            throw new NullPointerException("array points is null");
        
        } else {
            // Copy array to aux[] (for immutability) and check for null points in points
            aux = new Point[points.length];
            int countPoint = 0;
            for (Point point: points) {
                if (point == null)
                    throw new NullPointerException("point value is null");
                else
                    aux[countPoint++] = point;
            }
            // check for duplicate points in aux array
            if (duplicates(aux)) 
                throw new IllegalArgumentException("duplicate points in points array");
            
            // Calculate line segments
            bagOfSegments = new Bag<LineSegment>();
            
            st1 = new ST<Point, LineSegment>();   
            st2 = new ST<Point, LineSegment>();   

            
            // we take our pivot point from points array but pass in the sorted array aux to the slopes method
            for (int i = 0; i < aux.length; i++)
                slopes(aux, points[i]);
           
        }
    }  
    // return the number of segments
    public int numberOfSegments()
    {
        return bagOfSegments.size();
    }
    
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[bagOfSegments.size()];
        int count = 0;
        for (LineSegment ls: bagOfSegments)
            segments[count++] = ls;
        return segments;
    }
    
    // helper function: Sort array then checks for duplicates items
    private static boolean duplicates(Comparable[] a) {
        Arrays.sort(a);

        for (int i = 1; i < a.length; i++) {
            if (a[i].equals(a[i-1]))
                return true;   
        }
        return false;
    }
    
    // Helper function: Sort the array according to the gradient with point p
    private void slopes(Point[] a, Point p) {

        // Sort the array a according to the slopes they make with point p
        Arrays.sort(a, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                
                if (p.slopeTo(p1) == p.slopeTo(p2)) return 0;
                if (p.slopeTo(p1) < p.slopeTo(p2)) return -1;
                return 1;
            }
        });
        
        // Find LineSegments
        for (int j = 3; j < a.length; j++) {  
          
          if (a[0].slopeOrder().compare(a[j-2], a[j]) == 0
                  && a[0].slopeOrder().compare(a[j-1], a[j]) == 0
                  && a[0].slopeTo(a[j]) == a[j].slopeTo(a[j-2])
                  && a[0].slopeTo(a[j]) == a[j].slopeTo(a[j-1]) 
                  ) {  
              
              // add the first four points to a bag
              Bag<Point> bag = new Bag<Point>();
              bag.add(a[0]);
              bag.add(a[j-2]);
              bag.add(a[j-1]);
              bag.add(a[j]);
              
              // This loop finds the segments with more than 4  points
              int count = 1;
              while (j + count < a.length) {
                  if (a[0].slopeOrder().compare(a[j], a[j + count]) == 0
                          && a[0].slopeTo(a[j]) == a[j].slopeTo(a[j + count])) 
                      bag.add(a[j + count++]);     
                  else 
                      break;
              }
              
              // Convert the bag of Points into an array so that we can sort by Point
              // This would not be necessary if the original sort earlier was "stable"
              Point[] shortSort = new Point[bag.size()];
              int i = 0;
              for (Point point: bag) {
                  shortSort[i++] = point; 
              }
              // Sort shortArray
              Arrays.sort(shortSort);
              // Point p i.e. a[0]
              Point key1 = shortSort[0];
              // furthest point from p
              Point key2 = shortSort[bag.size() -1];
              
              // if we have not come across these points
              // which make up a line segment 
              // add them to our cache
              // and create  a new line segment
              if (!st1.contains(key1)  || !st2.contains(key2)) {    
                  LineSegment lineSegment = new LineSegment(key1, key2);
                  bagOfSegments.add(lineSegment);
                  st1.put(key1, lineSegment);
                  st2.put(key2, lineSegment);
              }
           // Once we have found a segment we can break from the loop
              // Is this a correct assumption?
           break;   
          }    
      } 
    }
    
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
     // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("#segments: " + collinear.numberOfSegments());
        
        
    }
}
