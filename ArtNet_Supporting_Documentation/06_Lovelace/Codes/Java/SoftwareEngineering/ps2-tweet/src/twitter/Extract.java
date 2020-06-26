package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.StringTokenizer;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        
        // assess that there are zero items in tweets
        final Instant d1 = Instant.parse("2000-01-01T00:00:00Z");
        if (tweets.size() == 0 ) 
            return new Timespan(d1,d1);
        // assess that there is one item in tweets
        if (tweets.size() == 1 )
            return new Timespan(tweets.get(0).getTimestamp(), tweets.get(0).getTimestamp());
            
        
        // Find earliest start and latest end of the list of tweets
        Tweet tweet1 = tweets.get(0);
        Instant start = tweet1.getTimestamp();
        Instant end = tweet1.getTimestamp();
        
        for (Tweet tweet: tweets) {
            if (tweet.getTimestamp().compareTo(start) < 0) {
                start = tweet.getTimestamp();                
            }
            if (tweet.getTimestamp().compareTo(end) > 0) {
                end = tweet.getTimestamp();
            }
        }
        
        return new Timespan(start, end);
                
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        
        Set<String> mentions = new HashSet<String>();       // Set of user names
        for (Tweet tweet: tweets) {                         // loop through tweets
            String str = tweet.getText().toLowerCase();     // convert to lower case
            StringTokenizer st = new StringTokenizer(str, " \t\n\r\f,.:;?![]'");
            while (st.hasMoreElements()) {
                String nextElement = st.nextElement().toString();
                TwitterUsernameValidator validator = new TwitterUsernameValidator();
                if (validator.validate(nextElement)) { // check to see if username is a valid Twitter username
                    String token = nextElement.toString();
                    int lastIndex = token.length();
                    // Check for Multiple @s
                    int AtCount = 0;
                    for (int i = 0; i < token.length(); i++ ) {
                        if ( token.charAt(i) == '@') 
                            AtCount++;
                    }
                    mentions.add(token.substring(AtCount, lastIndex));
                }    
            }
        }
        
        // return set of usernames mentioned in tweets
        return mentions;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
