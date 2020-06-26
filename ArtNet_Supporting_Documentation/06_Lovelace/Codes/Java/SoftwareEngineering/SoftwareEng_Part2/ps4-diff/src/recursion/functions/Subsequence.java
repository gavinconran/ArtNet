package recursion.functions;

import java.io.File;

public class Subsequence {
    
    public static String subsequences(String word) {
        if (word.isEmpty()) {
            return ""; // base case
        } else {
            char firstLetter = word.charAt(0);
            String restOfWord = word.substring(1);
                 
            String subsequencesOfRest = subsequences(restOfWord);
                 
            String result = "";
            for (String subsequence : subsequencesOfRest.split(",", -1)) {
                result += "," + subsequence;
                result += "," + firstLetter + subsequence;
            }
            result = result.substring(1); // remove extra leading comma
            return result;
       }
   }
    
    /**
     * @param n integer to convert to string
     * @param base base for the representation. Requires 2<=base<=10.
     * @return n represented as a string of digits in the specified base, with 
     *           a minus sign if n<0.  No unnecessary leading zeros are included. 
     */
    public static String stringValue(int n, int base) {
        if (n < 0) {
            return "-" + stringValue(-n, base);
        } else if (n == 0) {
            return "";
        } else {
            return stringValue(n/base, base) + "0123456789ABCDEF".charAt(n%base);
        }
    }
    
    /**
     * @param f a file in the filesystem
     * @return the full pathname of f from the root of the filesystem
     */
    public static String fullPathname(File f) {
        if (f.getParentFile() == null) {
            // base case: f is at the root of the filesystem
            return f.getName();  
        } else {
            // recursive step
            return fullPathname(f.getParentFile()) + "/" + f.getName();
        }
    }
}
