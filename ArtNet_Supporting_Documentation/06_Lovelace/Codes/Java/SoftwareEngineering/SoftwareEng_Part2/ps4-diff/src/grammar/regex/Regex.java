package grammar.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static void main(String[] args) {
        
        String string = "http://didit.csail.mit.edu:4949/";
        
        Pattern regex = Pattern.compile("http://([a-z]+\\.)+[a-z]+(:[0-9]+)?/");
        Matcher m = regex.matcher(string);
        if (m.matches()) {
            System.out.println("Match");
        } else {
            System.out.println("No Match");
        }

    }

}
