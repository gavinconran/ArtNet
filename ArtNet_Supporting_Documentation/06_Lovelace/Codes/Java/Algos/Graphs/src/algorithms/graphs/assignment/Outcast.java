package algorithms.graphs.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
    private WordNet wordNet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        
        if (wordnet == null)
            throw new NullPointerException("WordNet is null");
        
        this.wordNet = wordnet; 
        
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null)
            throw new NullPointerException("nouns is null");
        
        String maxNoun = nouns[0];
        int maxDist = 0;
        for (String noun: nouns) {
            int testDist = 0;
            for (String noun2: nouns) {
                if (!noun.equals(noun2)) {
                    testDist += wordNet.distance(noun2, noun);
                }   
            }
            if (testDist > maxDist) {
                maxDist = testDist;
                maxNoun = noun;
            }
        }
        return maxNoun;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }

}
