package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     * 
     * buy(Book book):
     *      Valid and invalid input
     * checkOut(BookCopy copy) 
     *      Valid and invalid input
     * checkIn(BookCopy copy)
     *      Valid and invalid input
     * isAvailable(BookCopy copy):
     *      Valid and invalid input
     * allCopies():
     *      Valid and invalid input    
     * available():
     *      Valid and invalid input     
     * find():
     *      Valid and invalid input 
     * lose()
     *      Valid and invalid input           
     *         
     */
    
    // JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void emptyLibraryTest() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    
    @Test
    public void buyTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = library.buy(book);
        assertTrue(library.isAvailable(copy));
    }
    
    @Test
    public void buyTest2() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book1);        
        library.buy(book1);
        assertEquals(2, library.allCopies(book1).size());
    }
    
    @Test
    public void checkOutTest1() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy1 = library.buy(book1);        
        library.checkout(copy1);
        assertFalse(library.isAvailable(copy1));
    }
    
    @Test
    public void checkOutTest2() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy1 = library.buy(book1); 
        library.checkout(copy1);
        assertFalse(library.isAvailable(copy1));
        library.checkin(copy1);
        assertTrue(library.isAvailable(copy1));
    }
    
    @Test
    public void checkInTest1() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy1 = library.buy(book1); 
        library.checkout(copy1);
        assertFalse(library.isAvailable(copy1));
        
        library.checkin(copy1);
        assertTrue(library.isAvailable(copy1));
    }
    
    @Test
    public void checkInTest2() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy1 = library.buy(book1); 
        library.buy(book1); 
        library.checkout(copy1);
        
        assertFalse(library.isAvailable(copy1));
        assertEquals(2, library.allCopies(book1).size());
        assertEquals(1, library.availableCopies(book1).size());
    }
    
    @Test
    public void isAvailableTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = library.buy(book);
        assertTrue(library.isAvailable(copy));
    }
    
    @Test
    public void isAvailableTest2() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = library.buy(book);
        library.checkout(copy);
        
        assertFalse(library.isAvailable(copy));
        assertEquals(1, library.allCopies(book).size());
        assertEquals(0, library.availableCopies(book).size());
    }
    
    @Test
    public void allCopiesTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book);
        assertEquals(1, (library.allCopies(book)).size());
        
        library.buy(book);
        assertEquals(2, (library.allCopies(book)).size());
    }
    
    @Test
    public void allCopiesTest2() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        Book book2 = new Book("This Test Is Just An Example 2", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book);
        
        assertEquals(0, (library.allCopies(book2)).size());
    }
    
    @Test
    public void availableCopiesTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = library.buy(book);
        library.checkout(copy);
        assertEquals(0, (library.availableCopies(book)).size());
        
        library.buy(book);
        assertEquals(1, (library.availableCopies(book)).size());
    }
    
    @Test
    public void availableCopiesTest2() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        Book book2 = new Book("This Test Is Just An Example 2", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book);
        
        assertEquals(0, (library.availableCopies(book2)).size());
    }
    
    @Test
    public void findTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book);
        assertEquals(1, (library.find(book.getTitle()).size()));
        
        library.buy(book);
        assertEquals(1, (library.find(book.getAuthors().get(1)).size()));  
        assertEquals(book.getAuthors().get(1), library.find(book.getAuthors().get(1)).get(0).getAuthors().get(1)); 
        assertEquals(book.getAuthors().get(1), library.find(book.getAuthors().get(0)).get(0).getAuthors().get(1));
    }
    
    @Test
    public void findTest2() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        library.buy(book);
        
        assertEquals(1, (library.find("This Test Is Just An Example")).size());
    }
    
    @Test
    public void loseTest1() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = library.buy(book);
        library.checkout(copy);
        assertFalse(library.availableCopies(book).contains(book));
        assertTrue(library.allCopies(book).contains(copy));
        
        library.lose(copy);
        assertFalse(library.availableCopies(book).contains(book));
        assertFalse(library.allCopies(book).contains(book));
        
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
