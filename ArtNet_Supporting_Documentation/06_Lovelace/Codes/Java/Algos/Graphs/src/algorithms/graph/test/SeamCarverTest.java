package algorithms.graph.test;

import static org.junit.Assert.*;

import java.awt.Color;

import algorithms.graphs.assignment.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarverTest {
    
    Picture picture1;
    Picture nullPicture;
    Picture pictureWidth1;
    Picture pictureHeight1;
    Picture picture5by6;
    Picture picture6by5;
    Picture picture7by10;
    Picture picture1by8;
    Picture picture8by1;
    Picture picture3by7;
    
    Picture chameleon;
    Picture HJocean;
    
    int x1;
    int y1;
    
    double energy1;
    
    int[] nullSeam = null;
    int[] validSeamHeight7 = new int[7];
    int[] validSeamHeight8 = new int[8];
    int[] validSeamHeight10 = new int[10];
    
    int[] invalidSeam = new int[7];

    @Before
    public void setUp() throws Exception {
        
        nullPicture = null;
        picture1 = new Picture("/home/gavin/Java/Algos/week8/data/bird_small.png");
        pictureWidth1 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/1x8.png");
        pictureHeight1 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/8x1.png");
        picture5by6 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/5x6.png");
        picture6by5 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/6x5.png");
        picture7by10 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/7x10.png");
        picture1by8 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/1x8.png");
        picture8by1 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/8x1.png");
        picture3by7 = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/3x7.png");
        
        chameleon = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/chameleon.png");
        HJocean = new Picture("/home/gavin/Java/Algos/week8/data/seamCarving/HJocean.png");
        
        invalidSeam[0] = -1;
        invalidSeam[1] = 0;
        invalidSeam[2] = 1;
        invalidSeam[3] = 0;
        invalidSeam[4] = 0;
        invalidSeam[5] = 1;
        invalidSeam[6] = 0;
        
        validSeamHeight7[0] = 1;
        validSeamHeight7[1] = 0;
        validSeamHeight7[2] = 1;
        validSeamHeight7[3] = 0;
        validSeamHeight7[4] = 0;
        validSeamHeight7[5] = 1;
        validSeamHeight7[6] = 0;
        
        validSeamHeight8[0] = 1;
        validSeamHeight8[1] = 0;
        validSeamHeight8[2] = 1;
        validSeamHeight8[3] = 0;
        validSeamHeight8[4] = 0;
        validSeamHeight8[5] = 1;
        validSeamHeight8[6] = 0;
        validSeamHeight8[7] = 0;
        
        validSeamHeight10[0] = 1;
        validSeamHeight10[1] = 0;
        validSeamHeight10[2] = 1;
        validSeamHeight10[3] = 0;
        validSeamHeight10[4] = 0;
        validSeamHeight10[5] = 1;
        validSeamHeight10[6] = 0;
        validSeamHeight10[7] = 0;
        validSeamHeight10[8] = 0;
        validSeamHeight10[9] = 1;
        
        x1 = 5;
        y1 = 6;
    
    }

    @After
    public void tearDown() throws Exception {
        
        picture1 = null;
        picture5by6 = null;
        picture6by5 = null;
        picture1by8 = null;
        picture8by1 = null;
        invalidSeam =null;
        validSeamHeight7 = null;
        validSeamHeight8 = null;
        validSeamHeight10 = null;
        
        chameleon = null;
        HJocean = null;
        
    }
    
    @Test(expected=NullPointerException.class)
    public void testSeamCarverConstructorNulPointerException() {
        
        new SeamCarver(nullPicture);
    }
    
    @Test
    public void testSeamCarverConstructor() {
        
        new SeamCarver(picture1);
    }
    
    @Test
    public void testPicture() {
        
        SeamCarver sc = new SeamCarver(picture1);
        assertTrue(sc.picture().equals(picture1));
        assertFalse(sc.picture() == picture1);
    }
    
    @Test
    public void testWidth() {
        
        SeamCarver sc = new SeamCarver(picture1);
        assertTrue(sc.picture().width() == picture1.width());
    }
    
    @Test
    public void testHeight() {
        
        SeamCarver sc = new SeamCarver(picture1);
        assertTrue(sc.picture().height() == picture1.height());
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSeamCarverEnergyColumnIndexOutOfBoundsException() {
        
        SeamCarver sc = new SeamCarver(picture1);
        sc.energy(-1, sc.height());
    }
    
    @Test
    public void testSeamCarverEnergy() {
        
        SeamCarver sc = new SeamCarver(picture1);
        assertTrue(sc.energy(0, sc.height()-2) == 1000);
        assertTrue(sc.energy(1, sc.height()-1) == 1000);
        assertTrue(sc.energy(sc.width() - 2, 0) == 1000);
        assertTrue(sc.energy(sc.width() - 1, 1) == 1000);
        
        Color colourX1 = sc.picture().get(x1 + 1, y1);
        Color colourX2 = sc.picture().get(x1 - 1, y1);
        int Rx = colourX1.getRed() - colourX2.getRed();
        int Gx = colourX1.getGreen() - colourX2.getGreen();
        int Bx = colourX1.getBlue() - colourX2.getBlue();
        double deltaXsquared = Rx * Rx + Gx * Gx + Bx * Bx;
        
        Color colourY1 = sc.picture().get(x1, y1 + 1);
        Color colourY2 = sc.picture().get(x1, y1 - 1);
        int Ry = colourY1.getRed() - colourY2.getRed();
        int Gy = colourY1.getGreen() - colourY2.getGreen();
        int By = colourY1.getBlue() - colourY2.getBlue();
        double deltaYsquared = Ry * Ry + Gy * Gy + By * By;
        energy1 = Math.sqrt(deltaXsquared + deltaYsquared);
        
        assertTrue(sc.energy(x1, y1) == energy1);
    }
    
    @Test
    public void testSeamCarverEnergy2() {
        
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture6by5.width(), picture6by5.height());
        
        SeamCarver sc = new SeamCarver(picture6by5);
        
        StdOut.printf("Printing energy calculated for each pixel.\n");        

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++)
                StdOut.printf("%9.0f ", sc.energy(i, j));
            StdOut.println();
        }
        
        int[] verticalSeam = sc.findVerticalSeam();
        for (int j = 0; j < sc.height(); j++) {
            StdOut.println(verticalSeam[j]);
        }    
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSeamCarverEnergyRowIndexOutOfBoundsException() {
        
        SeamCarver sc = new SeamCarver(chameleon);
        sc.energy(sc.width(), -1);
    }
    
    @Test
    public void testfindHorizontalSeam() {
        
        SeamCarver sc = new SeamCarver(picture5by6);
        assertTrue(sc.findHorizontalSeam().length == sc.width());
    }
    @Test
    public void testfindHorizontalSeam2() {
        
        SeamCarver sc = new SeamCarver(picture1by8);
        assertTrue(sc.findHorizontalSeam().length == sc.width());
    }
    
    @Test
    public void testfindHorizontalSeam3() {
        
        SeamCarver sc = new SeamCarver(picture8by1);
        StdOut.println("sc.findHorizontalSeam().length: " + sc.findHorizontalSeam().length);
        StdOut.println("sc.width(): " + sc.width());

        assertTrue(sc.findHorizontalSeam().length == sc.width());
    }
    
    @Test
    public void testfindVerticalSeam() {
        
        SeamCarver sc = new SeamCarver(picture6by5);
        assertTrue(sc.findVerticalSeam().length == sc.height());
    }
    
    @Test
    public void testfindVerticalSeam2() {
        
        SeamCarver sc = new SeamCarver(picture1by8);
        assertTrue(sc.findVerticalSeam().length == sc.height());
    }
    
    @Test
    public void testfindVerticalSeam3() {
        
        SeamCarver sc = new SeamCarver(picture8by1);
        assertTrue(sc.findVerticalSeam().length == sc.height());
    }
    
//    @Test
//    public void testPointToIndex() {
//        
//        SeamCarver sc = new SeamCarver(picture7by10);
//        assertTrue(sc.pointToIndex(0, 0) == 0);
//        assertTrue(sc.pointToIndex(1, 0) == 1);
//        assertTrue(sc.pointToIndex(6, 0) == 6);
//        
//        StdOut.println("(0,1): " + sc.pointToIndex(0, 1));
//        assertTrue(sc.pointToIndex(0, 1) == 7);
//        assertTrue(sc.pointToIndex(1, 1) == 8);
//        assertTrue(sc.pointToIndex(6, 1) == 13);
//    }
    
    @Test(expected=NullPointerException.class)
    public void testRemoveHorizontalSeamNullPointerException1() {
        
        SeamCarver sc = new SeamCarver(picture1);
        sc.removeHorizontalSeam(nullSeam);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveHorizontalSeamIllegalArgumentException2() {
        
        SeamCarver sc = new SeamCarver(pictureWidth1);
        sc.removeHorizontalSeam(invalidSeam);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveHorizontalSeamIllegalArgumentException3() {
        
        SeamCarver sc = new SeamCarver(picture1);
        sc.removeHorizontalSeam(validSeamHeight8);
    }
    
    @Test(expected=NullPointerException.class)
    public void testRemoveVerticalSeamNullPointerException1() {
        
        SeamCarver sc = new SeamCarver(picture1);
        sc.removeVerticalSeam(nullSeam);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveVerticalSeamIllegalArgumentException2() {
        
        SeamCarver sc = new SeamCarver(pictureWidth1);
        sc.removeVerticalSeam(invalidSeam);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveVerticalSeamIllegalArgumentException3() {
        
        SeamCarver sc = new SeamCarver(picture1);
        sc.removeVerticalSeam(validSeamHeight8);
    }
    
    @Test
    public void testRemoveVerticalSeam() {
        StdOut.println("0");
        SeamCarver sc = new SeamCarver(picture3by7);
        
//        for (int col = 0; col < sc.picture().width(); col++) {
//            for (int row = 0; row < sc.picture().height(); row++) {
//                StdOut.println(sc.picture().get(col, row));
//            }
//        }
        
        StdOut.println("Before");
        for (int col = 0; col < sc.picture().width(); col++) {
            for (int row = 0; row < sc.picture().height(); row++) {
                StdOut.println(sc.picture().get(col, row));
            }
        }
        sc.removeVerticalSeam(sc.findVerticalSeam());
        assertTrue(sc.picture().width() == picture3by7.width() - 1);
        
        StdOut.println("After");
        for (int col = 0; col < sc.picture().width(); col++) {
            for (int row = 0; row < sc.picture().height(); row++) {
                StdOut.println(sc.picture().get(col, row));
            }
        }
    }
    
    @Test
    public void testRemoveHorizontalSeam() {
        
        StdOut.println("height:" + picture6by5.height() );
        SeamCarver sc = new SeamCarver(picture6by5);
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        StdOut.println("height:" + sc.picture().height() );
//        assertTrue(sc.picture().height() == picture6by5.height() - 1);
        
        StdOut.println("Horizontal Seam: ");
        for (int col = 0; col < sc.picture().width(); col++) {
            for (int row = 0; row < sc.picture().height(); row++) {
                StdOut.println(sc.picture().get(col, row));
            }
        }
    }

}
