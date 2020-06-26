package algorithms.chapter2.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
import algorithms.chapter2.mergesort.Merge;
import algorithms.chapter2.quicksort.Quick;
import algorithms.chapter2.quicksort.Quick3way;

public class mergeTest {
    
    Comparable[] unSortArray;
    Comparable[] sortArray;
    
    Comparable[] unSortArrayPartition;
    Comparable[] unSortArrayPartition2;
    
    @Before
    public void setUp() throws Exception {
        
        unSortArray = new Comparable[10];
        unSortArray[0] = 75;
        unSortArray[1] = 56;
        unSortArray[2] = 85;
        unSortArray[3] = 90;
        unSortArray[4] = 49;
        unSortArray[5] = 26;
        unSortArray[6] = 12;
        unSortArray[7] = 48;
        unSortArray[8] = 40;
        unSortArray[9] = 47;
        
        sortArray = new Comparable[10];
        sortArray[0] = 12;
        sortArray[1] = 26;
        sortArray[2] = 40;
        sortArray[3] = 47;
        sortArray[4] = 48;
        sortArray[5] = 49;
        sortArray[6] = 56;
        sortArray[7] = 75;
        sortArray[8] = 85;
        sortArray[9] = 90;
        
        unSortArrayPartition = new Comparable[10];
        unSortArrayPartition[0] = 55;
        unSortArrayPartition[1] = 91;
        unSortArrayPartition[2] = 32;
        unSortArrayPartition[3] = 61;
        unSortArrayPartition[4] = 76;
        unSortArrayPartition[5] = 55;
        unSortArrayPartition[6] = 55;
        unSortArrayPartition[7] = 27;
        unSortArrayPartition[8] = 25;
        unSortArrayPartition[9] = 55;
        
        unSortArrayPartition2 = new Comparable[12];
        unSortArrayPartition2[0] = 67;
        unSortArrayPartition2[1] = 61;
        unSortArrayPartition2[2] = 64;
        unSortArrayPartition2[3] = 33;
        unSortArrayPartition2[4] = 57;
        unSortArrayPartition2[5] = 58;
        unSortArrayPartition2[6] = 73;
        unSortArrayPartition2[7] = 68;
        unSortArrayPartition2[8] = 53;
        unSortArrayPartition2[9] = 80;
        unSortArrayPartition2[10] = 86;
        unSortArrayPartition2[11] = 60;
               
    }

    @After
    public void tearDown() throws Exception {
        
        unSortArray = null;
        sortArray = null;
        
    }

    @Test
    public void testMergeSortTopDown() {
        // Print out unSorted array
        StdOut.println("unSorted: " + Merge.subarray(unSortArray)); 
        
        // sort the unSortarray
        Merge.sortTD(unSortArray);
        
        // assert that the unSorted array is now sorted and equal to sortArray
        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("sortedTest: " + Merge.subarray(unSortArray)); 
    }
    
    @Test
    public void testMergeSortBottomUp() {
        // Print out unSorted array
        StdOut.println("unSorted: " + Merge.subarray(unSortArray));  
        
        // sort the unSortarray
        Merge.sortBU(unSortArray);
        
        // assert that the unSorted array is now sorted and equal to sortArray
        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("sortedTest: " + Merge.subarray(unSortArray)); 
    }
    
    @Test
    public void testQuickSort() {
        // Print out unSorted array
        StdOut.println("unSorted: " + Merge.subarray(unSortArray));  
        
        // sort the unSortarray
        Quick.sort(unSortArray);
        
        // assert that the unSorted array is now sorted and equal to sortArray
        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("sortedTest: " + Merge.subarray(unSortArray)); 
    }
    
    @Test
    public void testQuickSelect() {
        // Print out unSorted array
        StdOut.println("unSorted: " + Merge.subarray(unSortArray));  
        
        // sort the unSortarray
        Comparable result = Quick.select(unSortArray, 2);
        
        // assert that the unSorted array is now sorted and equal to sortArray
        assertEquals(result, 40);
        
        // Printout sorted array
        StdOut.println("resultSelect: " + result); 
    }
    
    @Test
    public void testQuick3way() {
     // Print out unSorted array
        StdOut.println("unSorted: " + Merge.subarray(unSortArray));  
        
        // sort the unSortarray
        Quick3way.sort(unSortArray);
        
        // assert that the unSorted array is now sorted and equal to sortArray
        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("sortedTest: " + Merge.subarray(unSortArray)); 
    }
    
    @Test
    public void testQuick3wayPartition() {
     // Print out unSorted array
        StdOut.println("unSortArrayPartition: " + Merge.subarray(unSortArrayPartition));  
        
        // sort the unSortarray
        Quick3way.partition(unSortArrayPartition, 0, unSortArrayPartition.length - 1);
        
        // assert that the unSorted array is now sorted and equal to sortArray
//        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("SortArrayPartition: " + Merge.subarray(unSortArrayPartition)); 
    }
    
    @Test
    public void testQuickPartition() {
     // Print out unSorted array
        StdOut.println("unSortArrayPartition2: " + Merge.subarray(unSortArrayPartition2));  
        
        // sort the unSortarray
        Quick.partition(unSortArrayPartition2, 0, unSortArrayPartition2.length - 1);
        
        // assert that the unSorted array is now sorted and equal to sortArray
//        assertEquals(Merge.subarray(unSortArray), Merge.subarray(sortArray));
        
        // Printout sorted array
        StdOut.println("SortArrayPartition2: " + Merge.subarray(unSortArrayPartition2)); 
    }

}
