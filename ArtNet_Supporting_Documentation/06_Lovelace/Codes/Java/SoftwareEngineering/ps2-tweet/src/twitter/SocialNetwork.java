package twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> socialNetwork = new HashMap<String, Set<String>>();
         
        // Initialise socialNetwork Keys
        for (Tweet tweet: tweets) {
            socialNetwork.put(tweet.getAuthor(), new HashSet<String>());
        }
        
        for (Tweet tweet: tweets) {
            String follower = tweet.getAuthor();
            String str = tweet.getText();
            StringTokenizer st = new StringTokenizer(str);          // create a list of text tokens
            
            while (st.hasMoreElements()) {
                String nextElement = st.nextElement().toString();
                if (nextElement.charAt(0) == "@".charAt(0)) {  // check to see if token is a username
                    String token = nextElement.toString().toLowerCase();
                    int lastIndex = token.length();
                    String followed = token.substring(1, lastIndex);
                    
                    if ( socialNetwork.get(followed) != null ) {
                        Set<String> newSet = socialNetwork.get(followed); // add author to list of followed followers
                        newSet.add(follower);
                        socialNetwork.put(followed, newSet);   
                    }
                    
                }    
            }
        }
        
        return socialNetwork;
    }
    
    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param map
     *            a social network (as defined above)
     * @return a map ordered in descending order according to map values
     */
    private static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys. 
        //The comparator is used to sort the TreeMap by keys. 
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        
        List<String> influencers = new ArrayList<String>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        for (String followed: followsGraph.keySet()) {
            int count = followsGraph.get(followed).size();
            map.put(followed, count);
        }
        
        TreeMap<String, Integer> sortedMap = sortMapByValue(map);  
        
        for (String followed: sortedMap.keySet()) {
            influencers.add(followed);
        }
        
        return influencers;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
