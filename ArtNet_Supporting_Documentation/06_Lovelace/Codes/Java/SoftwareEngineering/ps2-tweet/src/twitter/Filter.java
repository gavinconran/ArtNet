package twitter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> authoredTweets = new ArrayList<Tweet>();
        // Loop through all tweets in list
        for (Tweet tweet: tweets) {
            if (tweet.getAuthor().compareToIgnoreCase(username) == 0)   {
                authoredTweets.add(tweet);
            }
        }
        // return Tweets authored by username        
        return authoredTweets;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> spanTweets = new ArrayList<Tweet>();
        // Loop through all tweets in list
        for (Tweet tweet: tweets) {
            if (tweet.getTimestamp().compareTo(timespan.getStart()) >= 0 &&
                    tweet.getTimestamp().compareTo(timespan.getEnd()) <= 0) {
                spanTweets.add(tweet);
            }
        }
        // return Tweets authored by username        
        return spanTweets;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> mentions = new ArrayList<Tweet>();       // List of Tweets
        Boolean[] array = new Boolean[tweets.size()];        // array to test if tweet already added to mentions list   
        Arrays.fill(array, Boolean.FALSE);
        int index = 0;
        
        for (Tweet tweet: tweets) {                          // loop through tweets
            StringTokenizer st = new StringTokenizer(tweet.getText()); // create a list of text tokens
            while (st.hasMoreElements()) {
                String nextElement = st.nextElement().toString();
                for (String word: words) {
                    if (word.compareToIgnoreCase(nextElement) == 0 &&
                            array[index] == false ) {
                        
                        mentions.add(tweet);
                        array[index] = true;
                        break;
                    }
                }    
            }
            index++;
        }
        
        // return set of usernames mentioned in tweets
        return mentions;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
