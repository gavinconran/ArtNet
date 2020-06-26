package expressivo;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Plus implements Expression {
    
    private final Expression left;

    private final Expression right;
        
    // Lazily initialized, cached hashCode
    private volatile int hashCode; 
        
    // Abstraction function
    //    represents the sum of two expressions left+right
    // Rep invariant
    //    true
    // Safety from rep exposure
    //    all fields are immutable and final
    
    // RepCheck Function
    private void RepCheck() {        
        assert left.toString().length() > 0; 
        assert right.toString().length() > 0; 
    }

    /** Create a new Plus Object */
    public Plus(Expression left, Expression right) {
        
        this.left = left;
        this.right = right;
        
        RepCheck();
    }
    
    /**
     * Add two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     */
    @Override
    public Expression plus(Expression left, Expression right) {
        
        return new Plus(left, right);
    }
    
    /**
     * Multiply two expressions.
     * @param left expression
     * @param right expression
     * @return IllegalArgumentException if the expression is invalid
     */
    @Override
    public Expression times(Expression let, Expression right) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * @return human-readable representation of this Expression
     */   
    @Override 
    public String toString() {
        return left + "+" + right;
//        return "(" + left + ")+(" + right + ")";
    }
    
    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Plus)) return false;
        Plus thatPlusExp = (Plus) that;
        return this.left.equals(thatPlusExp.left) &&
                this.right.equals(thatPlusExp.right) &&
                        this.left.hashCode() == thatPlusExp.left.hashCode() &&
                        this.right.hashCode() == thatPlusExp.right.hashCode();
    }
    
    @Override 
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 31 * result + (this.left.hashCode() + this.right.hashCode());
            hashCode = result;
        }
        return result;
   }
    
    /** @return the expression differentiated w.r.t a variable */
    @Override
    public Expression differentiate(String wrtVar) {
        return new Plus(left.differentiate(wrtVar), right.differentiate(wrtVar));
    }
    
    /** @return the simplified expression */
    @Override
    public Expression simplify(Map<String, Double> environment) {
        Expression returnValue =  new Plus(left.simplify(environment), right.simplify(environment));
        
        // if expression matches a plus operation with doubles regex then evaluate expression
        Pattern regex = Pattern.compile("([0-9]*\\.?[0-9]+([0-9]+)?)([+*])([0-9]*\\.?[0-9]+([0-9]+)?)");
        Matcher m = regex.matcher(returnValue.toString());
        if (m.matches()) {
                double evalPlus = Double.parseDouble(m.group(1)) + Double.parseDouble(m.group(4));
                returnValue = new Term(String.valueOf(evalPlus));
        }
        
        return returnValue;
    }

}
