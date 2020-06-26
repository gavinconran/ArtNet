package library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import library.BookCopy.Condition;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // safety from rep exposure argument
    //      allCopies had a defensive copy
    //      availableCopies has a defensive copy
    //      find has a defensive copy
    
    public SmallLibrary() {
        this.inLibrary = new HashSet<BookCopy>();
        this.checkedOut = new HashSet<BookCopy>();
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        Set<BookCopy> intersection = new HashSet<BookCopy>(inLibrary); // use the copy constructor
        intersection.retainAll(checkedOut);
        assert intersection.isEmpty();
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.GOOD);
        inLibrary.add(copy);
        checkRep();
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        checkedOut.add(copy);
        inLibrary.remove(copy);
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        checkedOut.remove(copy);
        inLibrary.add(copy);
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> inLib = new HashSet<BookCopy>(); // use the copy constructor
        for (BookCopy inCopy : inLibrary) {
            if (inCopy.getBook().equals(book))
                inLib.add(inCopy);                
        }
        
        Set<BookCopy> outCheck = new HashSet<BookCopy>(); // use the copy constructor
        for (BookCopy inCopy : checkedOut ) {
            if (inCopy.getBook().equals(book))
                outCheck.add(inCopy);                
        }
        
        inLib.addAll(outCheck);
        return inLib;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> inLib = new HashSet<BookCopy>(); // use the copy constructor
        for (BookCopy inCopy : inLibrary) {
            if (inCopy.getBook().equals(book))
                inLib.add(inCopy);                
        }
        return inLib;
    }

    @Override
    public List<Book> find(String query) {
        Set<Book> results = new HashSet<Book>();
        // Title match
        for ( BookCopy copy : inLibrary ) {
            if (copy.getBook().getTitle() == query) {
                results.add(copy.getBook());
            }
        }
        // Author match
        for ( BookCopy copy : inLibrary ) {
            for (String author : copy.getBook().getAuthors())
                if (author == query) {
                    results.add(copy.getBook());
            }
        }
        return new ArrayList<Book>(results);
    }
    
    @Override
    public void lose(BookCopy copy) {
        checkedOut.remove(copy);
        inLibrary.remove(copy);
    }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
