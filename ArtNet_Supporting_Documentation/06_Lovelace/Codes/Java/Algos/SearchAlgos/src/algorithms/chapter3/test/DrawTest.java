package algorithms.chapter3.test;

import algorithms.chapter3.kdtree.PointSET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class DrawTest {
    
    public static void main(String[] args) {
        
        PointSET pointSETTest1;
        
//        String filename = args[0];
//        In in = new In(filename);
        
        
        
        In inTest1 = new In("/home/gavin/Java/Algos/week5/assignment/kdtree/horizontal8.txt");
        pointSETTest1 = new PointSET();
        while (!inTest1.isEmpty()) {
            double x = inTest1.readDouble();
            double y = inTest1.readDouble();
            Point2D p = new Point2D(x, y);
            pointSETTest1.insert(p);
        }
        
        StdDraw.show(0);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        
        pointSETTest1.draw();
        StdDraw.show();  

    }

}