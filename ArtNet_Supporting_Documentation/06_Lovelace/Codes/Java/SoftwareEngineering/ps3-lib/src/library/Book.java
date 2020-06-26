package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // rep
    private final String title;
    private final List<String> authors;
    private final int year;
    
    // Lazily initialized, cached hashCode
    private volatile int hashCode; 
    
    // rep invariant
        // title.length > 0. Title must contain at least one non-space character
        // authors.size() > 0, nd each name must contain at least one non-space character.
        // year >= 0
    // abstraction function
        // Title: represents the set of characters found in A 
        // Authors: List of representations of the set of characters found in A
        // Year: represents a non-negative integer 
    // safety from rep exposure argument
        // All fields are private and final
        // title is an immutable String
        // authors is an unmodifiable list
        // year is an immutable int
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        List<String> authorsCopy = new ArrayList<String>(authors);
        this.authors = Collections.unmodifiableList(authorsCopy);
        this.year = year;
        
        RepCheck();
    }
    
    // RepCheck Function
    private void RepCheck() {
        
        assert title.length() > 0; 
        InputValidator inputValidator = new InputValidator();
        assert inputValidator.validate(this.title);
        assert authors.size() > 0;
        for (String author : this.authors)
            assert inputValidator.validate(author);
        assert year >= 0;
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return this.authors;
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Title: " + this.title + "\n");
        s.append("Authors: " + "\n");
        for (String author : this.authors )
            s.append("  " + author + "\n");  
        s.append("Year: " + String.format("%d\n", this.year));
        return s.toString();
    }

    // Effective Java: Item 8: Obey the general contract when overriding equals
     @Override
     public boolean equals(Object that) {
         if (!(that instanceof Book)) return false;
         Book thatBook = (Book) that;
//         return (this.getTitle() == thatBook.getTitle() 
//                 && this.getAuthors() == thatBook.getAuthors()
//                 && this.year == thatBook.year);
         return (this.getTitle().equals(thatBook.getTitle()) 
                 && this.getAuthors().equals(thatBook.getAuthors())
                 && this.year == thatBook.year);
     }
     
     // Effective Java: Item 9: Always override hashCode when you override equals
     @Override 
     public int hashCode() {
         int result = hashCode;
         if (result == 0) {
             result = 17;
             result = 31 * result + title.hashCode();
             result = 31 * result + authors.hashCode();
             result = 31 * result + year;
             hashCode = result;
         }
         return result;
    }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
