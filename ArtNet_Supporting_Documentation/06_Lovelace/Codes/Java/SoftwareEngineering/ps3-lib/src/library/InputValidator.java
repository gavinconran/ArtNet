package library;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN =  ".*\\w.*";

    public InputValidator(){
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    /**
     * Validate username with regular expression
     * @param username username for validation
     * @return true valid username, false invalid username
     */
    public boolean validate(final String username){

        matcher = pattern.matcher(username);
        return matcher.matches();

    }
 }
