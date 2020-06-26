package library;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Testing strategy for this ADT should go here.
     * getTitle():
     *      Valid and invalid input
     * getAuthors() 
     *      Valid and invalid input
     * getYear()
     *      Valid and invalid input
     * equals(Object that):
     *      Valid and invalid input
     * public int hashCode():
     *      Valid and invalid input               
     *           
     */
    
    // JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void getTitleTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertEquals("Title of Book", book.getTitle());
    }
    
    @Test
    public void getTitle2Test() {
        Book book = new Book(" A ", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertEquals(" A ", book.getTitle());
    }
    
    @Test(expected=AssertionError.class)
    public void getTitleInvalidTest1() {
        @SuppressWarnings("unused")
        Book book = new Book("", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
    }
    
    @Test(expected=AssertionError.class)
    public void getTitleInvalidTest2() {
        @SuppressWarnings("unused")
        Book book = new Book("Title of Book", Arrays.asList(), 1990);
    }
    
    @Test(expected=AssertionError.class)
    public void getTitleInvalidTest3() {
        @SuppressWarnings("unused")
        Book book = new Book("Title of Book", Arrays.asList("", "author2 Author", "a3 author"), 1990);
    }
    
    @Test(expected=AssertionError.class)
    public void getTitleInvalidTest4() {
        @SuppressWarnings("unused")
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), -1990);
    }
    
    @Test
    public void getAuthorsTest() {
        Book book3 = new Book("Title of Book", Arrays.asList(" a", "author2 Author", "a3 author"), 1990);
        assertEquals(3, book3.getAuthors().size());
        assertEquals(" a", book3.getAuthors().get(0));
        assertEquals("author2 Author", book3.getAuthors().get(1));
        assertEquals("a3 author", book3.getAuthors().get(2));       
    }
    
    @Test(expected=AssertionError.class)
    public void getAuthorsInvalidTest1() {
        @SuppressWarnings("unused")
        Book book3 = new Book("Title of Book", Arrays.asList(), 1990);   
    }
    
    @Test(expected=AssertionError.class)
    public void getAuthorsInvalidTest2() {
        @SuppressWarnings("unused")
        Book book3 = new Book("Title of Book", Arrays.asList("a", "", "a3 author"), 1990);   
    }
    
    @Test
    public void getYearTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertEquals(1990, book.getYear());
    }
    
    @Test
    public void getYearTest2() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 0);
        assertEquals(0, book.getYear());
    }
    
    @Test(expected=AssertionError.class)
    public void getYearInvalidTest() {
        @SuppressWarnings("unused")
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), -1990);
    }
    
    @Test
    public void toStringTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        System.out.println(book.toString());
        assert true;
    }
    
    @Test
    public void equalsTest() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        Book book2 = book1;
        assertTrue(book1.equals(book2));
    }
    
    @Test
    public void equalsTest2() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        Book book2 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertTrue(book1.equals(book2));
    }
    
    @Test
    public void equalsTest3() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        Book book2 = new Book("Title of Book Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertFalse(book1.equals(book2));
    }
    
    @Test
    public void hashCodeTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertEquals(1836071274, book.hashCode()); 
    }
    
    @Test
    public void hashCodeTest2() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        Book book2 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertEquals(book1.hashCode(), book2.hashCode()); 
    }
    
    @Test
    public void hashCode3Test3() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        Book book2 = new Book("Title of Book this book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        assertNotEquals(book1.hashCode(), book2.hashCode()); 
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
