package Expression.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    
    enum inputType { EXPRESSION,
        DIFFERENTIATE,
        SIMPLIFY,
        INVALID
    }
    
    /**
     * Helper Static Method: regex(String input)
     * @param input: input from user
     * @return: the input Type
     */
    public static inputType regex(String input) {
        
        // Pattern Matchers for Differentiation and Simplify command
        Pattern regexDiff = Pattern.compile("!d/d[a-z|A-Z]+");
        Pattern regexSimp = Pattern.compile("!simplify( [a-z|A-Z]+=[0-9]+(\\.\\d+)?)*");

        Matcher mD = regexDiff.matcher(input);
        Matcher mS = regexSimp.matcher(input);
        
        // return input Type
        if (mD.matches())
            return inputType.DIFFERENTIATE;
        else if (mS.matches())
            return inputType.SIMPLIFY;
        else // if Not Differentiate or Simplify then assume input is an expression
            return inputType.EXPRESSION;

    }
    
    /**
     * Helper Static Function: regexDiff(String input)
     * @param input: !d/d command from user
     * @return w.r.t variable
     */
    public static String regexDiff(String input) {
        
        Pattern regexDiff = Pattern.compile("!d/d[a-z|A-Z]+");   
        Matcher mD = regexDiff.matcher(input);
        
        int offSet = 4;  // length of !d/d command
        String wrtVar;
        if (mD.matches()) {
            wrtVar = mD.group().substring(offSet);
            return new String(wrtVar);
        }

        return "";
    }
    
    /**
     * Helper Static Method: regexSimp(String input)
     * @param input: !simplify command from user
     * @return Map consisting of Variable (Key) Value (value) pairs 
     */
    public static Map<String, Double> regexSimp(String input) {
        
        Pattern regexSimp = Pattern.compile("!simplify( [a-z|A-Z]+=[0-9]+(\\.\\d+)?)*");
        Matcher mS = regexSimp.matcher(input);
        
        // map contains Variable (key) Value (value) pairs
        Map<String, Double> map = new HashMap<String, Double>();
        
        if (mS.matches()) { // if there is a match
            String[] tokens = mS.group().split(" ");
            for (String token : tokens) {
              
              if (token.indexOf("=") != -1) { // Don't include !simplify command
                  // Get variable
                 int equalsIndex = token.indexOf("=");
                 String var = token.substring(0, equalsIndex);
                    
                 // Get value and put Variable value pair into map
                 String value = token.substring(equalsIndex + 1);
                 map.put(var, Double.valueOf(value));
              }
            }    
        }
        return new HashMap<String, Double>(map);
    }
}
