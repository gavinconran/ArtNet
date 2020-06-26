package library;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // rep
    private Book book;
    private Condition condition;
    
    // rep invariant
        // Book must be of type Book
        // Condition must be of enum type
    // TODO: abstraction function
    // TODO: safety from rep exposure argument
    // abstraction function
        // Book: represents the set of parameters found in A
        // Condition: represents state of the Book
    // safety from rep exposure argument
        // All fields are private
        // Book is Mutable but defensive Copy is in place
        // Condition is Mutable but there is no need to make defensive enum copies
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.book = new Book(book.getTitle(), book.getAuthors(), book.getYear());
        this.condition = Condition.GOOD;
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert book.getTitle().length() > 0; 
        InputValidator inputValidator = new InputValidator();
        assert inputValidator.validate(book.getTitle());
        assert book.getAuthors().size() > 0;
        for (String author : this.book.getAuthors())
            assert inputValidator.validate(author);
        assert this.book.getYear() >= 0;
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return new Book(this.book.getTitle(), this.book.getAuthors(), this.book.getYear());
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        
        return this.condition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(book.toString());
        s.append(("Condition: " + this.getCondition() + "\n").toLowerCase());
        return s.toString();
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
