package algorithms.graphs.assignment;

import java.awt.Color;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

public class SeamCarver {
    
    private static final double MAX_PIXEL_ENERGY = 1000.0;
    
    private Color[][] pictureArray; // the Picture Matrix
    private double[] distTo;
    private int[] edgeTo;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        
        if (picture == null)
            throw new NullPointerException("provided picture is incorrect");
        
        // Create a pictureArray of colours each pixel
        pictureArray = new Color[picture.width()][picture.height()];
        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                pictureArray[col][row] = picture.get(col, row);
            }
        }
    }
    
    // current picture
    public Picture picture() {
        
        // Create a new picture object
        Picture newPicture = new Picture(width(), height());
        
        // Build picture object using pictureArray and pictureCopy
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                newPicture.set(col, row, pictureArray[col][row]);
            }
        }
        return newPicture;
    }
    
    // width of current picture
    public     int width() {
        return pictureArray.length;
    }
    
    // height of current picture
    public     int height() {
        return pictureArray[0].length;
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        
        // Validate method parameters
        validateDimensions(x, y);
        
        // if (x,y) is in border return energy of 1000.0
        if (x == 0 || x == width() - 1
                || y == 0 || y == height() - 1)
            return 1000.0;
        else {
            // Calculate delta X and Y squared
            double deltaXsquared = getDeltaXSquared(x, y);
            double deltaYsquared = getDeltaYSquared(x, y);
            
            return Math.sqrt(deltaXsquared + deltaYsquared);   
        }   
    }
    
    // Helper function: Calculate Delta X squared
    private double getDeltaXSquared(int x, int y) {
        
        Color colourX1 = pictureArray[x+1][y];
        Color colourX2 = pictureArray[x - 1][y];
        int Rx = colourX1.getRed() - colourX2.getRed();
        int Gx = colourX1.getGreen() - colourX2.getGreen();
        int Bx = colourX1.getBlue() - colourX2.getBlue();
        return Rx * Rx + Gx * Gx + Bx * Bx;
        
    }
    
    // Helper function: Calculate Delta Y squared
    private double getDeltaYSquared(int x, int y) {
        
        Color colourY1 = pictureArray[x][y + 1];
        Color colourY2 = pictureArray[x][y - 1];
        int Ry = colourY1.getRed() - colourY2.getRed();
        int Gy = colourY1.getGreen() - colourY2.getGreen();
        int By = colourY1.getBlue() - colourY2.getBlue();
        return Ry * Ry + Gy * Gy + By * By;
        
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        
        // Make copy of pictureArray
        Color[][] copyPictureArray = pictureArray;
        
        // set dimensions of picture array
        int arrayWidth = width();
        int arrayHeight = height();
        
        // set dimensions of transposePicture array
        int transposeArrayWidth = height();
        int transposeArrayHeight = width();
        
        // transpose pictureArray
        Color[][] transposePictureArray = 
                new Color[transposeArrayWidth][transposeArrayHeight];
        for (int col = 0; col < arrayWidth; col++) {
            for (int row = 0; row < arrayHeight; row++) {
                transposePictureArray[row][col] = pictureArray[col][row];
            }
        }
        pictureArray = transposePictureArray;
        
        int[] horizontSeam = findVerticalSeam();
        
        pictureArray = copyPictureArray;
        
        return horizontSeam;
    }
    
    private void buildDigraph() {
        
        Digraph digraph = new Digraph(height() * width());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (col == 0) {
                    if (row+1 < height()) {
                        digraph.addEdge(pointToIndex(col, row), pointToIndex(col, row + 1));
                        if (col+1 < width())
                            digraph.addEdge(pointToIndex(col, row), pointToIndex(col + 1, row + 1));
                    }        
                } else if (col == width() - 1 && row+1 < height()) {
                    digraph.addEdge(pointToIndex(col, row), pointToIndex(col, row + 1));
                    digraph.addEdge(pointToIndex(col, row), pointToIndex(col - 1, row + 1));
                } else if (row+1 < height()) {
                    digraph.addEdge(pointToIndex(col, row), pointToIndex(col - 1, row + 1));
                    digraph.addEdge(pointToIndex(col, row), pointToIndex(col, row + 1));
                    digraph.addEdge(pointToIndex(col, row), pointToIndex(col + 1, row + 1));
                }
            }    
        }
        
        distTo = new double[digraph.V()];
        edgeTo = new int[digraph.V()];
        for (int v = 0; v < digraph.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
     // visit vertices in toplogical order
        Topological topological = new Topological(digraph);
        if (!topological.hasOrder())
            throw new IllegalArgumentException("Digraph is not acyclic.");
        for (int v : topological.order()) {
            if (v >= 0 && v < width())
                distTo[v] = MAX_PIXEL_ENERGY;
            for (int w : digraph.adj(v)) {
                relax(distTo, edgeTo, v, w);
            }        
        }
    }
    
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        
        // Test for single height and width pictures
        if (width() == 1)
            return new int[height()];
        
        if (height() == 1)
            return new int[1];
        
        // Build Digraph
        buildDigraph();
        
        // Find the pixel on bottom row which returns the min. energy route
        double minDist = height() * MAX_PIXEL_ENERGY;
        int minPixel = -1;
        for (int i = width() * (height() - 1); i < height() * width(); i++) {
            if (distTo[i] < minDist) {
                minDist = distTo[i];
                minPixel = i;
            }
        }
        
        // Return the min. energy seam
        int[] minSeam = new int[height()]; 
        minSeam[height() - 1] = minPixel;
        // Create the min seam in terms of index
        int w = minPixel;
        for (int i = height() - 2; i >= 0; i--) {
            minSeam[i] = edgeTo[w];
            w = edgeTo[w];
        }
        
        // convert the minSeam array into the correct format, i.e. column only
        for (int j = 0; j < height(); j++)
            minSeam[j] = minSeam[j] % width();
        int[] seam = minSeam;
        return seam;
        
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        
        if (seam == null)
            throw new NullPointerException("provided seam is incorrect");
        
        if (seam.length != width())
            throw new IllegalArgumentException("provided seam is the incorrect width");
        
        if (height() < 2)
            throw new IllegalArgumentException("picture is not high enough");
        
        // check if array is not a valid seam
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1)
                throw new IllegalArgumentException("seam value " + seam[i] + 
                        " is not between 0 and " + (height() -1));
            if (i < seam.length - 1) {
                if (Math.abs(seam[i] - seam[i+1]) > 1)
                    throw new IllegalArgumentException("provided seam is out of range"); 
            }  
        }
        
        // To write removeHorizontalSeam(), 
        // transpose the image, 
        
        // set dimensions of picture and energy arrays
        int arrayWidth = width();
        int arrayHeight = height();
        
        // set dimensions of transposePicture and transposeEnergy arrays
        int transposeArrayWidth = height();
        int transposeArrayHeight = width();
        
        // transpose pictureArray
        
        pictureArray = createTransposePictureArray(transposeArrayWidth,
                transposeArrayHeight,
                arrayWidth,
                arrayHeight);
        
        // call removeVerticalSeam(), and 
        removeVerticalSeam(seam);
        
        // transpose it back. 
        // transpose back pictureArray
        pictureArray = createTransposeBackPictureArray(arrayWidth,
                                                        arrayHeight);
        
    }
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        
        if (seam == null)
            throw new NullPointerException("provided seam is incorrect");
        
        if (seam.length != height())
            throw new IllegalArgumentException("provided seam is the incorrect height");
        
        if (width() < 2)
            throw new IllegalArgumentException("picture is not wide enough");
        
        // check if array is not a valid seam
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width() - 1)
                throw new IllegalArgumentException("seam value " + seam[i] 
                        + " is not between 0 and " + (width() -1));
            if (i < seam.length - 1) {
                if (Math.abs(seam[i] - seam[i+1]) > 1)
                    throw new IllegalArgumentException("provided seam is out of range"); 
            }  
        }
        
        // set dimensions of reducedEnergy array
        int reducedArrayWidth = width() - 1;
        int reducedArrayHeight = height();
        
        // Initialise the reduced Picture Array
        Color[][] reducedPictureArray = 
                new Color[reducedArrayWidth][reducedArrayHeight];
        
        SET<Integer> delCols = new SET<Integer>();
        Color color;
        for (int col = 0; col < reducedArrayWidth; col++) {
            for (int row = 0; row < reducedArrayHeight; row++) {  
                if ((delCols.contains(row)) || seam[row] == col) {
                    color = pictureArray[col+1][row]; 
                    delCols.add(row);
                    
                } else 
                    color = pictureArray[col][row];
                
                reducedPictureArray[col][row] =  color;
            }
        }
        pictureArray = reducedPictureArray; 
        
    } 
    
    // Helper function
    private Color[][] createTransposePictureArray(int transposeArrayWidth,
                                                    int transposeArrayHeight,
                                                    int arrayWidth,
                                                    int arrayHeight) {
        Color[][] transposePictureArray = new Color[transposeArrayWidth][transposeArrayHeight];
        for (int col = 0; col < arrayWidth; col++) 
            for (int row = 0; row < arrayHeight; row++) 
                transposePictureArray[row][col] = pictureArray[col][row];
        
        return transposePictureArray;    
    }
    
 // Helper function
    private Color[][] createTransposeBackPictureArray(int arrayWidth,
                                                    int arrayHeight) {
        Color[][] transposeBackPictureArray = 
                new Color[arrayWidth][arrayHeight-1];
        for (int col = 0; col < arrayWidth; col++) 
            for (int row = 0; row < arrayHeight - 1; row++) 
                transposeBackPictureArray[col][row] = pictureArray[row][col];

        return transposeBackPictureArray;
    }
    
    // Helper Function: relax Moriarty!!
    private void relax(double[] distTo, int[] edgeTo, int v, int w) {
        // set width to convert from index value to (col,row) values
        int arrayWidth = width();

        if (distTo[w] > distTo[v] + energy(w % arrayWidth, w / arrayWidth)) {
            distTo[w] = distTo[v] + energy(w % arrayWidth, w / arrayWidth);
            edgeTo[w] = v;
        }       
    }
    
    // Helper Function: throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateDimensions(int col, int row) {
        
        // validate column 
        if (col < 0 || col > width() - 1)
            throw new IndexOutOfBoundsException("column " + col + " is not between 0 and " + (width() -1));
        
        // validate column 
        if (row < 0 || row > height() - 1)
            throw new IndexOutOfBoundsException("row " + row + " is not between 0 and " + (width() - 1));
    }
    
    // Helper function: Converts a Point in the openGrid to an index in the fullGrid
    private int pointToIndex(int col, int row) {
            return col + (row * width());
    } // end pointToIndex method

}