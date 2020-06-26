package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * Partition Strategy:
     * Vary number of tweets in tweets List
     * 
     * Create a Map using guessFollowsGraph to create Maps
     * 
     * Use different usernames
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:30:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:30:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T01:00:00Z");
    private static final Instant d7 = Instant.parse("2016-02-17T03:30:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "gavin", "@alyssa #gavin", d3);
    private static final Tweet tweet4 = new Tweet(4, "conran", "@bbitdiddle @gavin #gavin", d4);
    private static final Tweet tweet5 = new Tweet(5, "peter", "is it reasonable to talk about rivest so much?", d5);
    private static final Tweet tweet7 = new Tweet(7, "ben", "@peter #gavin", d7);
    
    private static final String user1 = "gavin";
    private static final String user2 = "alyssa";
    private static final String user3 = "bbitdiddle";
    private static final String user4 = "conran";
    private static final String user5 = "peter";
    private static final String user6 = "ben";
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphTwoTweet() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertEquals("expected 2 item", 2, followsGraph.size());
    }
    
    @Test
    public void testGuessFollowsGraphTwoTweetOrdered() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3));
        
        assertTrue("expected set of followers", followsGraph.get(user2).contains(user1));
    }
    
    @Test
    public void testGuessFollowsGraphFourTweet() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3, tweet5, tweet7));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertEquals("expected 4 item", 4, followsGraph.size());
    }
    
    @Test
    public void testGuessFollowsGraphFourTweetOrdered() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3, tweet5, tweet7));
        
        assertTrue("expected set of followers", followsGraph.get(user2).contains(user1));
        assertTrue("expected set of followers", followsGraph.get(user5).contains(user6));    
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersNonEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertFalse("expected non-empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencerSorderedList() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals("expected same order", 0, influencers.indexOf(user1));
        assertEquals("expected same order", 1, influencers.indexOf(user2));
        assertEquals("expected same order", 2, influencers.indexOf(user3));
        assertEquals("expected same order", 3, influencers.indexOf(user4));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
