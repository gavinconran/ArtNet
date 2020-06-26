package algorithms.chapter3.kdtree;

import java.util.Iterator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    // (red-black) bst based on SET
    private SET<Point2D> bst;
    
    
    // Constructor
    public PointSET() {
        
        bst = new SET<Point2D>();
        
    }
    
    /**
     * Returns the number of keys in this bst.
     *
     * @return the number of keys in this bst
     */
    public int size() {
        return bst.size();
    }
    
    /**
     * Returns true if the bst is empty.
     *
     * @return <tt>true</tt> if this set is empty, and <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Adds the key to the bst (if it is not already present).
     *
     * @param  key the key to add
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("callinsert() with a null point");
        bst.add(p);
    }
    
    /**
     * Returns true if this bst contains the given key.
     *
     * @param  key the key
     * @return <tt>true</tt> if this bst contains <tt>point</tt> and
     *         <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>point</tt> is <tt>null</tt>
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("called contains() with a null point");
        return bst.contains(p);
    }
    
    // draw all points to the standard draw
    public void draw() {
        
        StdDraw.show(0);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        
        for (Point2D p: bst) {
            p.draw();
        }
        StdDraw.show();   
    }
    
    // returns all points in a rectangle
    public Iterable<Point2D> range(RectHV rect) {
        
        return new Point2DIterable(rect);
    }
    
    private class Point2DIterable implements Iterable<Point2D> {
        
        private Bag<Point2D> bagOfRangePoints = new Bag<Point2D>();
        
        private Point2DIterable(RectHV rect) {
            
            for (Point2D p: bst) {
                if (rect.contains(p)) 
                    bagOfRangePoints.add(p);
                
            }
        }

        @Override
        public Iterator<Point2D> iterator() {
            Iterator<Point2D> iter = bagOfRangePoints.iterator();
            return iter; 
        }    
    }
    
    
    // return the point in bst which is nearest to p
    public Point2D nearest(Point2D p) {
        
        if (p == null) throw new NullPointerException("called contains() with a null point");
        
        Point2D nearest = null;
        // max distance between 2 point is Math.sqrt(2) = 1.41.....
        double nearestDistance = 1.5;
        
        for (Point2D point: bst) {
            if (point.distanceTo(p) < nearestDistance) {
                nearest = point;
                nearestDistance = point.distanceTo(p);
            }        
        }
        return nearest;          
    }

}
