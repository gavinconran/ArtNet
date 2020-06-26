package library;

import org.junit.Test;

import library.BookCopy.Condition;

import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Testing strategy for this ADT should go here.
     * getCondition():
     *      Valid and invalid input
     * equals(Object that):
     *      Valid and invalid input
     * public int hashCode():
     *      Valid and invalid input               
     *           
     */
    
    // JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book.getTitle(), copy.getBook().getTitle());
        assertEquals(book.getAuthors(), copy.getBook().getAuthors());
        assertEquals(book.getYear(), copy.getBook().getYear());
    }
    
    @Test
    public void getConditionTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(Condition.GOOD, copy.getCondition());
        
        copy.setCondition(Condition.DAMAGED);
        assertEquals(Condition.DAMAGED, copy.getCondition());

    }
    
    @Test
    public void getConditionTest2() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.DAMAGED);
        assertNotEquals(Condition.GOOD, copy.getCondition());
    }
    
    @Test
    public void toStringTest() {
        Book book = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.GOOD);
        System.out.println(copy.toString());
        assert true;
    }
    
    @Test
    public void equalsTest() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy1 = new BookCopy(book1);
        copy1.setCondition(Condition.GOOD);
        BookCopy copy2 = copy1;
        assertTrue(copy1.equals(copy2));
    }
    
    @Test
    public void equalsTest2() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy1 = new BookCopy(book1);
        Book book2 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy2 = new BookCopy(book2);
        assertFalse(copy1.equals(copy2));
    }
    
    @Test
    public void equalsTest3() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy1 = new BookCopy(book1);
        Book book2 = new Book("Title of Book Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy2 = new BookCopy(book2);
        assertFalse(copy1.equals(copy2));
    }
    
    @Test
    public void hashCodeTest() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy1 = new BookCopy(book1);
        assertNotEquals(584634336, copy1.hashCode()); 
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void hashCodeTest2() {
        Book book1 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy1 = new BookCopy(book1);
        Book book2 = new Book("Title of Book", Arrays.asList("a", "author2 Author", "a3 author"), 1990);
        BookCopy copy2 = new BookCopy(book2);
        assertNotEquals(copy1.hashCode(), copy2.hashCode()); 
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
