package algorithms.chapter2.assignments;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    private Bag<LineSegment> bagOfLineSegments;
    private Point[] aux;
            
    // Constructor
    public BruteCollinearPoints(Point[] points) {
        
        if (points == null) {
            throw new NullPointerException("array points is null");
        
        } else {
            // Check for null points
            aux = new Point[points.length];
            int count = 0;
            for (Point point: points) {
                if (point == null)
                    throw new NullPointerException("point value is null");
                else
                    aux[count++] = point;
            }
            // check for duplicate points in points
            if (duplicates(aux)) 
            throw new IllegalArgumentException("duplicate points in points array");
            
            bagOfLineSegments = new Bag<LineSegment>();
            // Calculate segments & numberOfSegments
            for (int i = 0; i < aux.length; i++)
                for (int j = i + 1; j < aux.length; j++)
                    for (int k = j+1; k < aux.length; k++)
                        if (aux[i].slopeOrder().compare(aux[j], aux[k]) == 0)  {
                            for (int l = k+1; l < aux.length; l++) { 
                                if (aux[i].slopeOrder().compare(aux[j], aux[l]) == 0) {
                                    bagOfLineSegments.add(new LineSegment(aux[i], aux[l]));
                                }        
                        }
                    }
        }
    }  
    
    public int numberOfSegments()
    {
        return bagOfLineSegments.size();
    }
    
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[bagOfLineSegments.size()];
        int count = 0;
        for (LineSegment ls: bagOfLineSegments)
            segments[count++] = ls;
        return segments;
    }
    
    // helper function: Sorts array then checks for duplicates
    private static boolean duplicates(Comparable[] a) {
        Quick3way.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i-1])   == 0)
                return true;   
        }
        return false;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("#segments: " + collinear.numberOfSegments());
    }
}
