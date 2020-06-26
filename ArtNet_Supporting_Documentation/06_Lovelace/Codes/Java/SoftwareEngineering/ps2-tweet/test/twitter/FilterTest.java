package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Partition Strategy:
     * Vary number of tweets in tweets List:
     * Empty, 1 item and more than 1 item
     * 
     * Vary username
     * 
     * Vary Instance variable, start and end
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:30:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:30:00Z");
    private static final Instant d5 = Instant.parse("2015-02-17T10:30:00Z");
    private static final Instant d6 = Instant.parse("2015-02-17T10:00:00Z");
    private static final Instant d7 = Instant.parse("2014-02-17T10:00:00Z");
    private static final Instant d8 = Instant.parse("2014-02-17T00:00:00Z");
    private static final Instant d9 = Instant.parse("2014-02-17T00:01:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "gavin", "@gavin #gavin", d3);
    private static final Tweet tweet4 = new Tweet(4, "gavin", "@conrangavin @gavin #gavin", d4);
    private static final Tweet tweet5 = new Tweet(5, "cowboy", "@conrangavin, @gavin #gavin @BILL", d5);
    private static final Tweet tweet6 = new Tweet(6, "bill", ",@conrangavin @gavin #gavin @BILL", d6);
    private static final Tweet tweet7 = new Tweet(7, "bill", ",@@@gavin", d7);
    private static final Tweet tweet8 = new Tweet(8, "Bill", ",@GAVIN, TAlK", d8);
    private static final Tweet tweet9 = new Tweet(9, "Peter", ",@GAVIN, talk helloworld", d9);
    
    private static final String user1 = "gavin";
    private static final String user2 = "alyssa";
    private static final String user3 = "bill";
    
    private static final String word1 = "talk";
    private static final String word2 = "helloworld";
    private static final String twoWords = "talk";
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMustBeCaseInsensitive() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet8), user3);
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet8));
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), user2);
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByNoTweetsNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(), user2);
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4), user1);
        
        assertEquals("expected two item list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResultOrdered() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4), user1);
        
        assertEquals("expected same order", 0, writtenBy.indexOf(tweet3));
        assertEquals("expected same order", 1, writtenBy.indexOf(tweet4));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResultsOrdered() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanNoTweetsNoResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(testStart, testEnd));
        
        assertTrue("expected non-empty list", inTimespan.isEmpty());
    }
    
    @Test
    public void testContainingMultipleSearchWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet9), Arrays.asList(word1, word2));
        
        assertEquals("expected singleton list", 1, containing.size());
        assertTrue("expected list to contain tweet", containing.contains(tweet9));
    }
    
    @Test
    public void testContainingMustBeCaseInsensitive() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet8), Arrays.asList(word1));
        
        assertEquals("expected singleton list", 1, containing.size());
        assertTrue("expected list to contain tweet", containing.contains(tweet8));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList(word1));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testContainingNoWord() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList(word2));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingOrdered() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList(word1));
        
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
