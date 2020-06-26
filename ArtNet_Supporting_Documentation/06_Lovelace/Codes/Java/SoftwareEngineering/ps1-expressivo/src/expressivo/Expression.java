package expressivo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import lib6005.parser.*;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    enum ExpressionGrammar{ROOT,
        PLUS,
        TIMES,
        PRIMITIVE,
        WHITESPACE,
        TERM};  
    
    // Datatype definition
    // Expression = Term(t:String)
    //       + Plus(left:Expression, right:Expression)
    //       + Times(left:Expression, right:Expression)

    // Creator
    public static Expression term(String t) {
        return new Term(t);
    }
    
    // Producers
    /**
     * Add two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     */
    public Expression plus(Expression left, Expression right);
    
    /**
     * Multiply two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     * @throws IllegalArgumentException if the expression is invalid
     */
    public Expression times(Expression let, Expression right);
      
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    
    /** @return the expression differentiated w.r.t a variable */
    public Expression differentiate(String wrtVar);
    
    /** @return the simplified expression as a String */
    public Expression simplify(Map<String, Double> environment);
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    
    public static Expression parse(String input) throws IllegalArgumentException {
        Expression result;
        try {
            result = buildAST(getParseTree(input));

        } catch (UnableToParseException | IOException e) {
            throw new IllegalArgumentException();
        } 
        return result;
    }
    
    /**
     * Parse a string into an integer arithmetic expression, displaying various
     * debugging output.
     * @throws If the string cannot be parsed, this method throws an UnableToParseException.
     * @throws If the grammar file DoubleExpression.g is not present, this will throw an IOException.
     * @throws If the grammar file is corrupted and cannot be parsed, the method will also throw an UnableToParseException.
     */
    public static ParseTree<ExpressionGrammar> getParseTree(String string) throws UnableToParseException, IOException{
         Parser<ExpressionGrammar> parser = GrammarCompiler.compile(new File("Expression.g"), ExpressionGrammar.ROOT);
         ParseTree<ExpressionGrammar> tree = parser.parse(string);
         
         return tree;
    }
    
    /**
     * Function converts a ParseTree to an Expression. 
     * @param p
     *  ParseTree<ExpressionGrammar> that is assumed to have been constructed by the grammar in Expression.g
     * @return
     */
    public static Expression buildAST(ParseTree<ExpressionGrammar> p){

        switch(p.getName()){
        /*
         * Since p is a ParseTree parameterized by the type ExpressionGrammar, p.getName() 
         * returns an instance of the ExpressionGrammar enum. This allows the compiler to check
         * that we have covered all the cases.
         */
        case TERM:
            /*
             * A number will be a terminal containing a term.
             */
            return new Term(p.getContents());
        case PRIMITIVE:
            /*
             * A primitive will have either a term or a sum as child (in addition to some whitespace)
             * By checking which one, we can determine which case we are in.
             */             

            if(p.childrenByName(ExpressionGrammar.TERM).isEmpty()){
                return buildAST(p.childrenByName(ExpressionGrammar.PLUS).get(0));
            }else{
                return buildAST(p.childrenByName(ExpressionGrammar.TERM).get(0));
            }
        
        case TIMES:
            /*
             * A product will have one or more children that need to be multiplied together.
             * Note that we only care about the children that are primitive. There may also be 
             * some whitespace children which we want to ignore.
             */
            boolean firstProd = true;
            Expression resultProd = null;
            for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRIMITIVE)){                
                if(firstProd){
                    resultProd = buildAST(child);
                    firstProd = false;
                }else{
                    resultProd = new Times(resultProd, buildAST(child));
                }
            }
            if(firstProd){ throw new RuntimeException("sum must have a non whitespace child:" + p); }
            return resultProd;    
            
        case PLUS:
            /*
             * A sum will have one or more children that need to be summed together.
             * Note that we only care about the children that are primitive. There may also be 
             * some whitespace children which we want to ignore.
             */
            boolean first = true;
            Expression result = null;
            for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.TIMES)){                
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
            return buildAST(p.childrenByName(ExpressionGrammar.PLUS).get(0));
        case WHITESPACE:
            /*
             * Since we are always avoiding calling buildAST with whitespace, 
             * the code should never make it here. 
             */
            throw new RuntimeException("You should never reach here:" + p);
        default:
            break;
        }   
        /*
         * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
         */
        throw new RuntimeException("You should never reach here:" + p);
    }


    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
