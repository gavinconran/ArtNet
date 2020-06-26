package algorithms.graphs.assignment;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    
    // Graph related
    private SET<String> nouns;  // bag of nouns
    private ST<String, SET<Integer>> st; // string -> indices
    private ST<String, Integer> stKeys;  // multiple word string -> index 
    private ST<Integer, String> extra;
    private String[] keys;      // index -> multiple word string
    private Digraph copyG;      // use copyG to keep Graph G immutable
    
    private SAP sap;        // sap object
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        if (synsets == null || hypernyms == null)
            throw new NullPointerException("filenames are incorrect");
        
        nouns = new SET<String>();
        st = new ST<String, SET<Integer>>(); 
        stKeys = new ST<String, Integer>(); 
        extra = new ST<Integer, String>();
        
        
        // build bag of nouns (nouns) 
        // and st (string -> indices)
        // and skKeys (multiple word string -> index) - inverse of keys
        In inSyn = new In(synsets);
        while (!inSyn.isEmpty()) {
            String[] a = inSyn.readLine().split(",");
            String[] synset = a[1].split(" ");
            int index = Integer.parseInt(a[0]);
            stKeys.put(a[1], index); // put unsplit sentence into stKeys
            extra.put(index, a[1]);
            // put split sentence, i.e. individual words into nous and st 
            for (int i = 0; i < synset.length; i++) {
                nouns.add(synset[i]);
                if (st.contains(synset[i])) { 
                    SET<Integer> tempSet = st.get(synset[i]);
                    tempSet.add(index);
                    st.put(synset[i], tempSet);
                }    
                else {  
                    SET<Integer> tempSet = new SET<Integer>();
                    tempSet.add(index);                
                    st.put(synset[i], tempSet);
                }    
            }
        }
        
        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : stKeys.keys()) {
            keys[stKeys.get(name)] = name;
        }
        
        // Builds the graph by connecting first vertex on each
        // line of hypernyms to all others
        final Digraph G = new Digraph(keys.length);
        In inHyp = new In(hypernyms);
        while (inHyp.hasNextLine()) {
            String[] a = inHyp.readLine().split(",");
            int v = Integer.parseInt(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = Integer.parseInt(a[i]);
                G.addEdge(v, w);
            }
        }
        
        // make use a copy of G -> making G immutable
        copyG = new Digraph(G);
        
        // check to make sure that the Graph is a rooted DAG
        int rootCount = 0;
        for (String word: st.keys()) {
            for (int v: st.get(word)) {
                if (copyG.outdegree(v) == 0)
                rootCount++;
            }    
        }
                
        if (rootCount != 1)
            throw new java.lang.IllegalArgumentException("he Digraph is not a rooted DAG");
        
        // Create a Shortest Ancestral Path (SAP) object
        sap = new SAP(copyG);
        
    } // end constructor
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        
        Stack<String> IterableNouns = new Stack<String>();
        for (String noun: nouns)
            IterableNouns.push(noun);
        return IterableNouns;
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        
        if (word == null)
            throw new NullPointerException("word is null");
        
        return nouns.contains(word);
    }
    
    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        
        if (nounA == null || nounB == null) 
            throw new NullPointerException("nounA or nounB is null");
        
        if (!nouns.contains(nounA) || !nouns.contains(nounB))
            throw new java.lang.IllegalArgumentException("either nounA or nounB is not a noun");
        

        return sap.length(st.get(nounA), st.get(nounB));
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        
        if (nounA == null || nounB == null)
            throw new NullPointerException("nounA or nounB is null");
        
        if (!nouns.contains(nounA) || !nouns.contains(nounB))
            throw new java.lang.IllegalArgumentException("either nounA or nounB is not a noun");
        
        int index = sap.ancestor(st.get(nounA), st.get(nounB));
        
        return extra.get(index); //keys[index]; 
    }
    

    public static void main(String[] args) {
        
        String filenameSynsets  = args[0];
        String filenameHypernyms = args[1];
        WordNet wordNet = new WordNet(filenameSynsets, filenameHypernyms);
        
        StdOut.println("root: " + wordNet.isNoun("entity"));
        
        int length   = wordNet.distance("A._Conan_Doyle", "B._B._King");
        String ancestor = wordNet.sap("A._Conan_Doyle", "B._B._King");
        StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
        
        int length1   = wordNet.distance("alimentation", "Gwyn");
        String ancestor1 = wordNet.sap("alimentation", "Gwyn");
        StdOut.printf("length1 = %d, ancestor1 = %s\n", length1, ancestor1);
        
        int length2   = wordNet.distance("genus_Pipilo", "genus_Phacochoerus");
        String ancestor2 = wordNet.sap("genus_Pipilo", "genus_Phacochoerus");
        StdOut.printf("length2 = %d, ancestor2 = %s\n", length2, ancestor2);

    }

}