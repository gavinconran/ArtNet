package grammar.parser.doubleandinteger;
// https://github.com/mit6005/fa16-ex18-parser-generators.git

/**
 * Key things a programmer must provide
 * 1: The Grammar: Stored in DoubleExpression.g
 * 2: Recursive Data Type: Interface defined in IntegerExpression.java
 *                         Base Class implemented in Number.java
 *                         Recursive Class implemented in Plus.java
 * 3: Write method buildAST which constructs the abstract Syntax Tree  
 * 4: Once the AST has been created the value of the input expression is computed by calling the IntegerExpression value() method                       
 */


import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;
import lib6005.parser.internal.GrammarCompiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

// Step 1: Import ParserLib library

public class IntExpParser {
    
    // Step 2: Define an Enum type
    enum IntegerGrammar{ROOT,
        SUM,
        PRODUCT,
        PRIMITIVE,
        PRIMARY,
        WHITESPACE,
        NUMBER};

        public static void main(String[] args) throws UnableToParseException, IOException {
            
            // Step 3: Create a parser
            // Programmer must provide the Grammar found in DoubleExpression.g
            Parser<IntegerGrammar> parser = GrammarCompiler.compile(
                    new File("DoubleExpression.g"), IntegerGrammar.ROOT);
            
            // Step 4: Call the Parser which matches a String to produce a ParseTree
            // Product Sum Combo
            String input1 = "54+(2*89)";
            ParseTree<IntegerGrammar> tree1 = parser.parse(input1);
            
            
            // Optional Step: Play around with ParseTree key methods
//            keyMethods(tree);
            
            // Step 5: Translate Concrete Syntax Tree into an Abstract Syntax Tree
            // Programmer must create the IntegerExpression Recursive Abstract Data Type
            // Programmer must provide the buildAST method
            IntegerExpression expr1 = buildAST(tree1);
            
            // Step 6: Compute Value of Expression
            double value1 = expr1.value();
            System.out.println(input1 + "=" + expr1 + "=" + value1);
            
            
            // All Steps in a method: Test 2
            // Product Sum Combo
            String input2 = "5*4*(3*6)+5";
            IntegerExpression expr2 = parse(input2);
            double value2 = expr2.value();
            System.out.println(input2 + "=" + expr2 + "=" + value2);
            
            // All Steps in a method: Test 2
            // Product Sum Combo
            String input3 = "(5*4)+5";
            IntegerExpression expr3 = parse(input3);
            double value3 = expr3.value();
            System.out.println(input3 + "=" + expr3 + "=" + value3);
            
            // All Steps in a method: Test 3
            // Product Sum Combo
            String input4 = "3 + 2.4";
            IntegerExpression expr4 = parse(input4);
            double value4 = expr4.value();
            System.out.println(input4 + "=" + expr4 + "=" + value4);
        
        }
        
        /**
         * Parse a string into an integer arithmetic expression, displaying various
         * debugging output.
         * @throws If the string cannot be parsed, this method throws an UnableToParseException.
         * @throws If the grammar file DoubleExpression.g is not present, this will throw an IOException.
         * @throws If the grammar file is corrupted and cannot be parsed, the method will also throw an UnableToParseException.
         */
        public static IntegerExpression parse(String string) throws UnableToParseException, IOException{
             Parser<IntegerGrammar> parser = GrammarCompiler.compile(new File("DoubleExpression.g"), IntegerGrammar.ROOT);
             ParseTree<IntegerGrammar> tree = parser.parse(string);
             // System.out.println(tree.toString());         
             IntegerExpression ast = buildAST(tree);
             return ast;
        }

    /**
     * Convert a parse tree into an abstract syntax tree.
     *
     * @param parseTree constructed according to the grammar in IntegerExpression.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static IntegerExpression makeAbstractSyntaxTree(final ParseTree<IntegerGrammar> parseTree) {
        switch (parseTree.name()) {
            case ROOT: // root ::= sum;
            {
                final ParseTree<IntegerGrammar> child = parseTree.children().get(0);
                return makeAbstractSyntaxTree(child);
            }

            case SUM: // sum ::= primary ('+' primary)*;
            {
                final List<ParseTree<IntegerGrammar>> children = parseTree.children();
                IntegerExpression expression = makeAbstractSyntaxTree(children.get(0));
                for (int i = 1; i < children.size(); ++i) {
                    expression = new Plus(expression, makeAbstractSyntaxTree(children.get(i)));
                }
                return expression;
            }

            case PRIMARY: // primary ::= number | '(' sum ')';
            {
                final ParseTree<IntegerGrammar> child = parseTree.children().get(0);
                // check which alternative (number or sum) was actually matched
                switch (child.name()) {
                    case NUMBER:
                        return makeAbstractSyntaxTree(child);
                    case SUM:
                        return makeAbstractSyntaxTree(child); // in this case, we do the
                    // same thing either way
                    default:
                        throw new AssertionError("should never get here");
                }
            }

            case NUMBER: // number ::= [0-9]+;
            {
                final int n = Integer.parseInt(parseTree.text());
                return new Number(n);
            }

            default:
                throw new AssertionError("should never get here");
        }

    }


    /**
         * Function converts a ParseTree to an IntegerExpression. 
         * @param p
         *  ParseTree<IntegerGrammar> that is assumed to have been constructed by the grammar in DoubleExpression.g
         * @return
         */
        public static IntegerExpression buildAST(ParseTree<IntegerGrammar> p){  //ParseTree<IntegerGrammar> parseTree

            switch(p.name()){
            /*
             * Since p is a ParseTree parameterized by the type IntegerGrammar, p.getName() 
             * returns an instance of the IntegerGrammar enum. This allows the compiler to check
             * that we have covered all the cases.
             */
            case NUMBER:
                /*
                 * A number will be a terminal containing a number.
                 */
                return new Number(Double.parseDouble(p.text()));
            case PRIMITIVE:
                /*
                 * A primitive will have either a number or a sum as child (in addition to some whitespace)
                 * By checking which one, we can determine which case we are in.
                 */             

                if(p.childrenByName(IntegerGrammar.NUMBER).isEmpty()){
//                    return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
                    return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
                }else{
                    return buildAST(p.childrenByName(IntegerGrammar.NUMBER).get(0));
                }
            
            case PRODUCT:
                /*
                 * A sum will have one or more children that need to be summed together.
                 * Note that we only care about the children that are primitive. There may also be 
                 * some whitespace children which we want to ignore.
                 */
                boolean firstProd = true;
                IntegerExpression resultProd = null;
                for(ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRIMITIVE)){                
                    if(firstProd){
                        resultProd = buildAST(child);
                        firstProd = false;
                    }else{
                        resultProd = new Times(resultProd, buildAST(child));
                    }
                }
                if(firstProd){ throw new RuntimeException("sum must have a non whitespace child:" + p); }
                return resultProd;    
                
            case SUM:
                /*
                 * A sum will have one or more children that need to be summed together.
                 * Note that we only care about the children that are primitive. There may also be 
                 * some whitespace children which we want to ignore.
                 */
                boolean first = true;
                IntegerExpression result = null;
                for(ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRODUCT)){                
                    if(first){
                        result = buildAST(child);
                        first = false;
                    }else{
                        result = new Plus(result, buildAST(child));
                    }
                }
                if(first){ throw new RuntimeException("sum must have a non whitespace child:" + p); }
                return result;
            case ROOT:
                /*
                 * The root has a single sum child, in addition to having potentially some whitespace.
                 */
//                return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
                return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
            case WHITESPACE:
                /*
                 * Since we are always avoiding calling buildAST with whitespace, 
                 * the code should never make it here. 
                 */
                throw new RuntimeException("You should never reach here:" + p);
            }   
            /*
             * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
             */
            throw new RuntimeException("You should never reach here:" + p);
        }
        
        /**
        * Traverse a parse tree, indenting to make it easier to read.
        * @param node
        * Parse tree to print.
        * @param indent
        * Indentation to use.
        */
       static void visitAll(ParseTree<IntegerGrammar> node, String indent){
           if(node.isTerminal()){  // base case
               System.out.println(indent + node.name() + ":" + node.text());
           }else{
               System.out.println(indent + node.name());
               // for each child in node
               for(ParseTree<IntegerGrammar> child: node){
                   visitAll(child, indent + "   ");  // recursive step
               }
           }
       }
       
       static void keyMethods(ParseTree<IntegerGrammar> node){
           
           System.out.println(node.toString());
           
           System.out.println("Tree Name: " + node.name());
           System.out.println("Tree Contents: " + node.text());
           System.out.println("Tree Children: " + node.children().toString());
           System.out.println("Tree Children: " + node.children().get(0).name().toString());
           System.out.println("Tree Children: " + node.children().get(0).text().toString());
           
           System.out.println("Tree Children: " + node.children().get(0).children().toString());
           System.out.println("Tree Children: " + node.children().get(0).children().get(0).toString());
           System.out.println("Tree Children: " + node.children().get(0).children().get(1).toString());
           System.out.println("Tree Children: " + node.children().get(0).children().get(2).toString());
           System.out.println("Tree Children: " + node.children().get(0).children().get(3).toString());
           //System.out.println("Tree Children: " + node.children().get(0).children().get(3).isTerminal());
           
           System.out.println("Tree Children: " + node.children().get(0).children().get(3).children().toString());
           System.out.println("Tree Children: " + node.children().get(0).children().get(3).children().get(0));
           //System.out.println("Tree Children: " + node.children().get(0).children().get(3).children().get(0).isTerminal());
           
           visitAll(node, "\t");
           
       }

}
