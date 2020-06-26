package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import library.BookCopy.Condition;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // rep
    private final Map<Book, Set<BookCopy>> inLibrary;
    private final Map<Book, Set<BookCopy>> checkedOut;
    
    // rep invariant:
    //    for all books the intersection of inLibrary.get(book) and checkedOut.get(book) is the empty set
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary.get(book) then it is available,
    //      and if a copy is in checkedOut.get(book) then it is checked out
    // safety from rep exposure argument
    //      allCopies has a defensive copy
    //      availableCopies has a defensive copy
    //      find has a defensive copy
    
    public BigLibrary() {
        this.inLibrary = new HashMap<Book, Set<BookCopy>>();
        this.checkedOut = new HashMap<Book, Set<BookCopy>>();
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        Set<BookCopy> intersection;
        
        for (Book book : inLibrary.keySet()) {
            intersection = new HashSet<BookCopy>(inLibrary.get(book)); 
            intersection.retainAll(checkedOut.get(book));
            assert intersection.isEmpty();
        }        
    }

    @Override
    public BookCopy buy(Book book) {
        
        // Set Condition of BookCopy to GOOD
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.GOOD);
        
        // Place Book in Map
        if ( this.inLibrary.containsKey(book) ) {
            Set<BookCopy> existingBookCopySet = this.inLibrary.get(book);
            existingBookCopySet.add(copy);
            this.inLibrary.replace(book, existingBookCopySet);              
        } else {
            Set<BookCopy> newBookCopySet = new HashSet<BookCopy>(); 
            newBookCopySet.add(copy);
            this.inLibrary.put(book, newBookCopySet);   
            Set<BookCopy> newBookCopySetOut = new HashSet<BookCopy>();
            checkedOut.put(book, newBookCopySetOut);
        }
        checkRep();
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        
        if ( checkedOut.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(checkedOut.get(copy.getBook())); // use the copy constructor
            bookCopySet.add(copy);   
            checkedOut.put(copy.getBook(), bookCopySet);
        }  
        
        if ( inLibrary.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(inLibrary.get(copy.getBook())); // use the copy constructor
            bookCopySet.remove(copy); 
            inLibrary.put(copy.getBook(), bookCopySet);
        } 
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if ( inLibrary.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(inLibrary.get(copy.getBook())); // use the copy constructor
            bookCopySet.add(copy); 
            inLibrary.put(copy.getBook(), bookCopySet);
        } 
        
        if ( checkedOut.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(checkedOut.get(copy.getBook())); // use the copy constructor
            bookCopySet.remove(copy);   
            checkedOut.put(copy.getBook(), bookCopySet);
        }   
        checkRep();
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        if ( inLibrary.containsKey(book) ) {
            Set<BookCopy> addition = new HashSet<BookCopy>(inLibrary.get(book)); // use the copy constructor
            addition.addAll(checkedOut.get(book));
            return addition;
        } else {   
            return  new HashSet<BookCopy>();  
        }     
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        if ( inLibrary.containsKey(book) ) {
            return new HashSet<BookCopy>(inLibrary.get(book)); 
        } else {   
            return  new HashSet<BookCopy>();  
        }          
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {

        if ( inLibrary.containsKey(copy.getBook()) ) {
            return inLibrary.get(copy.getBook()).contains(copy); 
        } else {   
            return  false; 
        }          
    }
    
    @Override
    public List<Book> find(String query) {
        Set<Book> results = new HashSet<Book>();
        // Title match
        for ( Book book : inLibrary.keySet() ) {
            for ( BookCopy copy : inLibrary.get(book) ) {
                    if (copy.getBook().getTitle().equals(query)) {
                        results.add(copy.getBook());
                    }   
            }
        }    
        // Author match
        for ( Book book : inLibrary.keySet() ) {
            for ( BookCopy copy : inLibrary.get(book) ) {
                for ( String author : copy.getBook().getAuthors() ) {
                    if (author == query) {
                        results.add(copy.getBook());
                    }
                }
            }
        }    
        return new ArrayList<Book>(results);
    }
    
    @Override
    public void lose(BookCopy copy) {
        if ( inLibrary.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(inLibrary.get(copy.getBook())); // use the copy constructor
            bookCopySet.remove(copy);   
            inLibrary.put(copy.getBook(), bookCopySet);
        }  
        
        if ( checkedOut.containsKey(copy.getBook()) ) {
            Set<BookCopy> bookCopySet = new HashSet<BookCopy>(checkedOut.get(copy.getBook())); // use the copy constructor
            bookCopySet.remove(copy);   
            checkedOut.put(copy.getBook(), bookCopySet);
        }   
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
