package minesweeper.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

   private Pattern pattern;
   private Matcher matcher;
   
   // regex for client to server communication
   private static final String INPUT_PATTERN = "(look)|(dig \\d+ \\d+)|(flag \\d+ \\d+)|" +
           "(deflag \\d+ \\d+)|(help)|(bye)";

   public InputValidator(){
       pattern = Pattern.compile(INPUT_PATTERN);
   }

   /**
    * Validate input with regular expression
    * @param username input for validation
    * @return true valid input, false invalid input
    */
   public boolean validate(final String input){

       matcher = pattern.matcher(input);
       return matcher.matches();

   }
}
