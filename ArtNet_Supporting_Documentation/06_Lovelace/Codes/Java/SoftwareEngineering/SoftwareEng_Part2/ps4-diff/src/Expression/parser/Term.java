package Expression.parser;

import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;
import lib6005.parser.internal.GrammarCompiler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Term implements Expression {
    
    enum ExpressionGrammar{ROOT,
        SUM,
        PRODUCT,
        PRIMITIVE,
        WHITESPACE,
        TERM};  
        
        
    // Lazily initialized, cached hashCode
    private volatile int hashCode;     
    
    private final String t;
    
    // Abstraction function
    //    represents the String t
    // Rep invariant
    //    true
    // Safety from rep exposure
    //    all fields are immutable and final
    
    /** Make an Expression. */
    public Term(String t) {
        this.t = t;
        
        RepCheck();
    }
    
    // RepCheck Function
    private void RepCheck() {        
        assert t.length() > 0; 
    }

    /**
     * @return an Abstract Syntax Tree version of this Concrete parsed Tree
     */
    @Override
    public Expression parse() {
        Expression result;
        try {
            result = buildAST(parse(this.t));

        } catch (UnableToParseException | IOException e) {
            result = new Term("Invalid Input");
        } 
        RepCheck();
        return result;
    }
    
    /**
     * @return a Differentiated Abstract Syntax Tree version of this Concrete parsed Tree
     */
    @Override
    public Expression differentiate(String wrtVar) {
        
        if (this.t.equals(wrtVar)) // it term is equals to w.r.t. variable
            return new Term("1");
        else
            return new Term("0"); // term is a non w.r.t. variable and therefore equal to 0
        
    }
    
    /**
     * @return a Simplified Abstract Syntax Tree version of this Concrete parsed Tree
     */
    @Override
    public Expression simplify(Map<String, Double> vars){
        
        if (vars.keySet().contains(t)) 
            return new Term(vars.get(t).toString());
        else
            return new Term(this.toString());
    }
    
    /**
     * @return human-readable representation of this Expression
     */    
    @Override 
    public String toString() {
        return String.valueOf(t);
    }
    
    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Term)) return false;
        Term thatTerm = (Term) that;
        return (this.t.equals(thatTerm.t));
    }
    
    @Override 
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + this.t.hashCode();
            hashCode = result;
        }
        return result;
   }
    
//    /**
//     * Parse a string into an integer arithmetic expression, displaying various
//     * debugging output.
//     * @throws If the string cannot be parsed, this method throws an UnableToParseException.
//     * @throws If the grammar file DoubleExpression.g is not present, this will throw an IOException.
//     * @throws If the grammar file is corrupted and cannot be parsed, the method will also throw an UnableToParseException.
//     */
    public static ParseTree<ExpressionGrammar> parse(String string) throws UnableToParseException, IOException{
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
                return buildAST(p.childrenByName(ExpressionGrammar.SUM).get(0));
            }else{
                return buildAST(p.childrenByName(ExpressionGrammar.TERM).get(0));
            }

        case PRODUCT:
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
                    resultProd = new TimesExp(resultProd, buildAST(child));
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
            Expression result = null;
            for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRODUCT)){
                if(first){
                    result = buildAST(child);
                    first = false;
                }else{
                    result = new PlusExp(result, buildAST(child));
                }
            }
            if(first){ throw new RuntimeException("sum must have a non whitespace child:" + p); }
            return result;
        case ROOT:
            /*
             * The root has a single sum child, in addition to having potentially some whitespace.
             */
            return buildAST(p.childrenByName(ExpressionGrammar.SUM).get(0));
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

}
