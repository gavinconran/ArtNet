// Code from https://www.mkyong.com/regular-expressions/how-to-validate-username-with-regular-expression/

package twitter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterUsernameValidator {

      private Pattern pattern;
      private Matcher matcher;

      private static final String USERNAME_PATTERN = "^@+[a-z0-9_-]+$";

      public TwitterUsernameValidator(){
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
