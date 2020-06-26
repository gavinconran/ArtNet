package Expression.parser;
// https://www.mkyong.com/java/how-to-read-input-from-console-java/

import java.io.IOException;
import lib6005.parser.UnableToParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;


public class ExpressionParser {
        
    public static void main(String[] args) throws UnableToParseException, IOException {
            
        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.print("Enter Expression : ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }
                    
                Expression expression;
                Expression parsedExp;
                
                // If an Expression    
                if (Regex.regex(input).equals(Regex.inputType.EXPRESSION)) {
                    expression = new Term(input);
                    parsedExp = expression.parse();
                    if (parsedExp.toString().equals("Invalid Input")){
                        System.out.println(parsedExp.toString());
                    }
                    else {
                        System.out.println(expression);
                        System.out.print("Enter Command: ");
                        
                        String command = br.readLine();
                        
                        // Deal with a Command input
                        switch(Regex.regex(command)){
                                
                        case DIFFERENTIATE:
                            // Extract w.r.t variable from command input
                            String wrtVar = Regex.regexDiff(command);
                            System.out.println("Differentiate " + parsedExp + " w.r.t " + wrtVar);
                            // NEXT STEP: RECURSIVELY DIFFERENTIATE COMMAND EXPRESSION
                            Expression diffExp = parsedExp.differentiate(wrtVar);
                            System.out.println("Expression " + parsedExp + " differentiated w.r.t " + wrtVar + " is " + diffExp);

                            break;
                                
                        case SIMPLIFY:                          
                            // Extract variable value pairs form command input
                            Map<String, Double> Vars = Regex.regexSimp(command); 
                            // Simply expression by Subing values for keys in the parsed expression
                            Expression simpExp = parsedExp.simplify(Vars); // Get Simplified expression
                            String environment = Vars.toString().substring(1, Vars.toString().length() - 1); // Get Environment
                            System.out.println("Expression " + parsedExp + " with environment " + environment + " is " + simpExp);
                            
                            break;
                                
                        default:
                            System.out.println("Invalid Command");
                            break;
                                
                        }    
                    }
                    
                }    
                System.out.println("-----------\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }    
    }
}
