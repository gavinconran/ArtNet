package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * Partitions:
     * getTimeSpan:
     * Test input cases will be: empty list, list with one item, two items, and lots of items
     * 
     * getMentionedUsers:
     * Test input cases will vary number of tweets in list and number of username mentions in each tweet 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:30:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:30:00Z");
    private static final Instant d5 = Instant.parse("2015-02-17T10:30:00Z");
    private static final Instant d6 = Instant.parse("2015-02-17T10:00:00Z");
    private static final Instant d7 = Instant.parse("2014-02-17T10:00:00Z");
    private static final Instant d8 = Instant.parse("2014-02-17T00:00:00Z");
    
    private static final String user1 = "gavin";
    private static final String user2 = "conrangavin";
    private static final String user3 = "bill";
    private static final String user1_Upper = "GAVIN";
    
    private static String textTweet9 = new StringBuilder()
            .append(",@")
            .append(user1_Upper)
            .toString();
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "gavin", "@gavin #gavin", d3);
    private static final Tweet tweet4 = new Tweet(4, "conran", "@conrangavin @gavin #gavin", d4);
    private static final Tweet tweet5 = new Tweet(5, "cowboy", "@conrangavin, @gavin #gavin @BILL", d5);
    private static final Tweet tweet6 = new Tweet(6, "bill", ",@conrangavin @gavin #gavin @BILL", d6);
    private static final Tweet tweet7 = new Tweet(7, "bill", ",@@@gavin", d7);
    private static final Tweet tweet8 = new Tweet(8, "bill", ",@GAVIN", d8);
    private static final Tweet tweet9 = new Tweet(9, "bill", textTweet9, d8);
    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test(expected=AssertionError.class)
    public void testGetTimespanNoTweets() {
        @SuppressWarnings("unused")
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        
        assert false;
    }
    
    @Test(expected=AssertionError.class)
    public void testGetTimespanOneTweet() {
        @SuppressWarnings("unused")
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assert false;
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets2() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet4));
        
        assertEquals("expected start", d4, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanManyTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanEmptyListAlwaysReturnsZerolengthTimespan() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        
        assertEquals("expected start", timespan.getStart(), timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", tweet1.getTimestamp(), timespan.getStart());
        assertEquals("expected end", tweet1.getTimestamp(), timespan.getEnd());

    }
    
    @Test
    public void testGetMentionedUsersMustBeCaseInsensitive() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
    }
    
    @Test
    public void testGetMentionedUsernameEndedByPunctuation() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertTrue("expected set contains 'conrangavin'", mentionedUsers.contains(user2));
    }
    
    @Test
    public void testGetMentionedUsernamePreceededByPunctuation() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected set contains 'conrangavin'", mentionedUsers.contains(user2));
    }
    
    @Test
    public void testGetMentionedUsersMultipleAtSigns() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
    }
    
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneMentionOneTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
    }
    
    @Test
    public void testGetMentionedUsersTwoMentionOneTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
        assertTrue("expected set contains 'conrangavin'", mentionedUsers.contains(user2));
        assertTrue("expected set 2 elements'", mentionedUsers.size() == 2);
    }
    
    @Test
    public void testGetMentionedUsersTwoMentionTwoTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
        assertTrue("expected set contains 'conrangavin'", mentionedUsers.contains(user2));
        assertTrue("expected set 2 elements'", mentionedUsers.size() == 2);
    }
    
    @Test
    public void testGetMentionedUsersTwoMentionFourTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4, tweet1, tweet2));
        
        assertTrue("expected set contains 'gavin'", mentionedUsers.contains(user1));
        assertTrue("expected set contains 'conrangavin'", mentionedUsers.contains(user2));
        assertTrue("expected set 2 elements'", mentionedUsers.size() == 2);
    }
    
    
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
