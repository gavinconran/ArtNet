package algorithms.strings.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {
    
    private TST<Integer> tst; // dictionary
    private TreeSet<String> realWords; // Collection of valid words
    private ST<Integer, Integer> scoreTable; // Scoring table
    private boolean[] markedDFS; // marked[v] = true if v is reachable from dice
    private StringBuilder buildWord; // Keep a record of letters in search
    
    private ST<Integer, List<Integer>> validTable; // valid die table
    private char[] indexToCharacter;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        
        // Create score table
        scoreTable = new ST<Integer, Integer>();
        scoreTable.put(0, 0);
        scoreTable.put(1, 0);
        scoreTable.put(2, 0);
        scoreTable.put(3, 1);
        scoreTable.put(4, 1);
        scoreTable.put(5, 2);
        scoreTable.put(6, 3);
        scoreTable.put(7, 5);
        scoreTable.put(8, 11);
        
        // make a copy of the dictionary
        String[] copyDictionary = new String[dictionary.length];
        System.arraycopy(dictionary, 0, copyDictionary, 0, dictionary.length);
        
        // Create a dictionary called tst (Trie)
        tst = new TST<Integer>();
        // Add words from dictionary to tst
        for (int i = 0; i < copyDictionary.length; i++) {
            // If word is a valid word add it to the dictionary set
            tst.put(copyDictionary[i], copyDictionary[i].length());
        }    
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        
        // Pre-compute valid die table and letter look up array
        validTable = new ST<Integer, List<Integer>>();
        indexToCharacter = new char[board.rows() * board.cols()];
        for (int r = 0; r < board.rows(); r++) {
            for (int c = 0; c < board.cols(); c++) {
                validTable.put(pointToIndex(board, r, c), getValidDie(board, r, c));
                indexToCharacter[pointToIndex(board, r, c)] = board.getLetter(r, c);
            }
        }
        
        // Create a set to collect words 
        realWords = new TreeSet<String>();
        // Search for all real words
        for (int index: validTable.keys()) {        
            buildWord = new StringBuilder();
            markedDFS = new boolean[board.rows() * board.cols()];
            // Once in the recursion we use the index rather than (r, c) co-ords
            dfs(board, index);   
        }
        return realWords;
    }
    
    // Depth First Search
    private void dfs(BoggleBoard board, int index) {
        
        // Add character(s) to buildWord
        char letter = indexToCharacter[index];
        
        // if buildWord contains a Q  we add a U
        if (letter == 'Q') { 
            buildWord.append(letter);
            buildWord.append('U');
        } else { // No Q present
            buildWord.append(letter);
        }   
        // set marked to true
        markedDFS[index] = true;
        
        // Recursive Base Case: if there are no prefixes of word then return
        String word = new String(buildWord);
        Iterable<String> savedTST = tst.keysWithPrefix(word);
        if (!savedTST.iterator().hasNext()) {
            // roll-back marked & buildWord
            if (buildWord.length() > 1 
                    && buildWord.charAt(buildWord.length()-2) == 'Q'
                    && buildWord.charAt(buildWord.length()-1) == 'U')
                buildWord.delete(buildWord.length()-2, buildWord.length());
            else // No Q present
                buildWord.deleteCharAt(buildWord.length()-1);
               
            markedDFS[index]  = false;
            return;
        }    
        
        // Capture words
        if (word.length() > 2 && tst.contains(word))
            realWords.add(word); 
        
        // Recurse through the grid
        for (Integer die: validTable.get(index))
            if (!markedDFS[die])
                dfs(board, die);  
                
        // Once we are done with letter(s) we must remove from string builder
        if (buildWord.length() > 1 
                && buildWord.charAt(buildWord.length()-2) == 'Q'
                && buildWord.charAt(buildWord.length()-1) == 'U')
            buildWord.delete(buildWord.length()-2, buildWord.length());
        else  // No Q present
            buildWord.deleteCharAt(buildWord.length()-1);
        // and unmark the grid   
        markedDFS[index] = false;
    }
            
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        // Return the score
        if (word.length() < 3 || !tst.contains(word)) // not a valid word
            return 0; 
        else if (word.length() > 8)
            return scoreTable.get(8);
        else 
            return scoreTable.get(word.length());
    }
    
    // Helper function: Converts a Point in the openGrid to an index in the fullGrid
    private int pointToIndex(BoggleBoard board, int row, int col) {
            return col + (row * board.cols());
    } // end pointToIndex method
    
    // Helper Function: Finds valid die from a given die
    private List<Integer> getValidDie(BoggleBoard board, int r, int c) {
       
        List<Integer> valid = new ArrayList<Integer>();
        // Go West
        if (c > 0) 
            valid.add(pointToIndex(board, r, c-1));
        
        // Go North West
        if (c > 0 && r > 0)   
            valid.add(pointToIndex(board, r-1, c-1));
            
        // Go South West
        if (c > 0 && r < board.rows() - 1) 
            valid.add(pointToIndex(board, r+1, c-1));
        
        // Go East
        if (c < board.cols() -1) 
            valid.add(pointToIndex(board, r, c+1));
           
        // Go North East
        if (c < board.cols() - 1 && r > 0) 
            valid.add(pointToIndex(board, r-1, c+1));
           
        // Go South East
        if (c < board.cols() -1 && r < board.rows() - 1) 
            valid.add(pointToIndex(board, r+1, c+1));
        
        // Go North
        if (r > 0) 
            valid.add(pointToIndex(board, r-1, c));
           
        // Go South
        if (r < board.rows() - 1) 
            valid.add(pointToIndex(board, r+1, c));
        
        return valid;
    }
}
